package io.storydoc.fabric.systemdescription.app;

import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SystemTypeDescriptorDTO;
import io.storydoc.fabric.systemdescription.domain.*;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.Environment;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemComponent;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemDescription;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SystemDescriptionService {

    private final SystemDescriptionStorage systemDescriptionStorage;
    private final SystemStructureCommandRunner systemStructureCommandRunner;
    private final SystemStructureHandlerRegistry systemStructureHandlerRegistry;

    public SystemDescriptionService(SystemDescriptionStorage systemDescriptionStorage, SystemStructureCommandRunner systemStructureCommandRunner, SystemStructureHandlerRegistry systemStructureHandlerRegistry) {
        this.systemDescriptionStorage = systemDescriptionStorage;
        this.systemStructureCommandRunner = systemStructureCommandRunner;
        this.systemStructureHandlerRegistry = systemStructureHandlerRegistry;
    }

    public SystemDescriptionDTO getSystemDescription() {
        SystemDescription systemDescription = systemDescriptionStorage.getOrCreateSystemDescription();
        return SystemDescriptionDTO.builder()
                .systemComponents(systemDescription.getSystemComponents().stream()
                        .map(this::getSystemComponentDTO)
                        .collect(Collectors.toList())
                )
                .environments(systemDescription.getEnvironments().stream()
                        .map(environment -> EnvironmentDTO.builder()
                                .key(environment.getKey())
                                .label(environment.getLabel())
                                .build())
                        .collect(Collectors.toList())
                )
                .settings(systemDescription.getSettings())
                .build();
    }

    private SystemComponentDTO getSystemComponentDTO(SystemComponent systemComponent) {
        return SystemComponentDTO.builder()
                .key(systemComponent.getKey())
                .label(systemComponent.getLabel())
                .systemType(systemComponent.getSystemType())
                .build();
    }

    public SystemComponentDTO getSystemComponentDTO(String systemComponentKey) {
        SystemDescription systemDescription = systemDescriptionStorage.getOrCreateSystemDescription();
        return systemDescription.getSystemComponents().stream()
                .filter(systemComponent -> systemComponent.getKey().equals(systemComponentKey))
                .findFirst()
                .map(this::getSystemComponentDTO)
                .orElse(null);

    }

    public String getSystemType(String systemComponentKey) {
        SystemDescription systemDescription = systemDescriptionStorage.getOrCreateSystemDescription();
        return systemDescription.getSystemComponents().stream()
                .filter(systemComponent -> systemComponent.getKey().equals(systemComponentKey))
                .findFirst()
                .map(SystemComponent::getSystemType)
                .orElse(null);
    }

    public void updateSystemDescription(SystemDescriptionDTO dto) {
        systemDescriptionStorage.saveSystemDescription(SystemDescription.builder()
                .environments(dto.getEnvironments().stream()
                        .map(environmentDTO -> Environment.builder()
                                .key(environmentDTO.getKey())
                                .label(environmentDTO.getLabel())
                                .build())
                        .collect(Collectors.toList())
                )
                .systemComponents(dto.getSystemComponents().stream()
                        .map(systemComponentDTO -> SystemComponent.builder()
                                .key(systemComponentDTO.getKey())
                                .label(systemComponentDTO.getLabel())
                                .systemType(systemComponentDTO.getSystemType())
                                .build()
                        )
                        .collect(Collectors.toList())
                )
                .settings(dto.getSettings())
                .build());
    }

    public StructureDTO getStructure(String environmentKey) {
        SystemDescriptionDTO systemDescription = getSystemDescription();

        return StructureDTO.builder()
                .structureType("root")
                .id(environmentKey)
                .children(
                        systemDescription.getSystemComponents().stream()
                                .map(systemComponentDTO -> systemStructureCommandRunner.getStructure(systemComponentDTO.getSystemType(), SystemComponentCoordinate.builder()
                                        .environmentKey(environmentKey)
                                        .systemComponentKey(systemComponentDTO.getKey())
                                        .build()))
                                .collect(Collectors.toList())
                )
                .build();
    }

    public StructureDTO getStructure(SystemComponentCoordinate coordinate) {
        SystemComponentDTO systemComponentDTO = getSystemComponentDTO(coordinate.getSystemComponentKey());
        return systemStructureCommandRunner.getStructure(systemComponentDTO.getSystemType(), coordinate);
    }

    public List<SystemTypeDescriptorDTO> getSystemTypeDescriptors() {
        return systemStructureHandlerRegistry.getHandlers().stream()
                .map(SystemStructureHandler::getSystemTypeDescriptor)
                .collect(Collectors.toList());
    }

    public EnvironmentDTO getEnvironmentDTO(String environmentKey) {
        SystemDescription systemDescription = systemDescriptionStorage.getOrCreateSystemDescription();
        return systemDescription.getEnvironments().stream()
                .filter(environment -> environment.getKey().equals(environmentKey))
                .findFirst()
                .map(environment -> EnvironmentDTO.builder()
                        .key(environment.getKey())
                        .label(environment.getLabel())
                        .build()
                )
                .orElse(null);
    }

    public Map<String, String> getSettings(SystemComponentCoordinate coordinate) {
        return systemDescriptionStorage.getOrCreateSystemDescription().getSettings()
                .get(coordinate.getEnvironmentKey())
                .get(coordinate.getSystemComponentKey());
    }

    public Map<String, String> getSettings(String systemComponentKey, String environmentKey) {
        SystemDescription systemDescription = systemDescriptionStorage.getOrCreateSystemDescription();
        return systemDescription.getSettings().get(environmentKey).get(systemComponentKey);
    }
}
