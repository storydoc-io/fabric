package io.storydoc.fabric.systemdescription.app;

import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import io.storydoc.fabric.systemdescription.app.systemtype.SystemTypeDescriptorDTO;
import io.storydoc.fabric.systemdescription.domain.SystemDescriptionStorage;
import io.storydoc.fabric.systemdescription.domain.SystemStructureCommandRunner;
import io.storydoc.fabric.systemdescription.domain.SystemStructureHandler;
import io.storydoc.fabric.systemdescription.domain.SystemStructureHandlerRegistry;
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
                .get();

    }

    public void updateSystemDescription(SystemDescriptionDTO dto) {
        this.systemDescriptionStorage.saveSystemDescription(SystemDescription.builder()
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

    public StructureDTO getStructure(String envKey) {
        SystemDescriptionDTO systemDescription = getSystemDescription();

        return StructureDTO.builder()
                .structureType("root")
                .id(envKey)
                .children(
                        systemDescription.getSystemComponents().stream()
                                .map(systemComponentDTO -> systemStructureCommandRunner.getStructure(systemComponentDTO))
                                .collect(Collectors.toList())
                )
                .build();
    }

    public StructureDTO getStructure(String envKey, String systemComponentKey) {
        SystemComponentDTO systemComponentDTO = getSystemComponentDTO(systemComponentKey);
        return systemStructureCommandRunner.getStructure(systemComponentDTO);
    }

    public List<SystemTypeDescriptorDTO> getSystemTypeDescriptors() {
        return this.systemStructureHandlerRegistry.getHandlers().stream()
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
                .get();
    }

    public Map<String, String> getSettings(String systemComponentKey, String environmentKey) {
        SystemDescription systemDescription = systemDescriptionStorage.getOrCreateSystemDescription();
        return systemDescription.getSettings().get(environmentKey).get(systemComponentKey);
    }
}
