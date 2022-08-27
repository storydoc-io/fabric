package io.storydoc.fabric.systemdescription.app;

import io.storydoc.fabric.systemdescription.app.structure.StructureDTO;
import io.storydoc.fabric.systemdescription.domain.SystemDescriptionStorage;
import io.storydoc.fabric.systemdescription.domain.SystemStructureCommandRunner;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.Environment;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemComponent;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemDescription;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SystemDescriptionService {

    private final SystemDescriptionStorage systemDescriptionStorage;
    private final SystemStructureCommandRunner systemStructureCommandRunner;

    public SystemDescriptionService(SystemDescriptionStorage systemDescriptionStorage, SystemStructureCommandRunner systemStructureCommandRunner) {
        this.systemDescriptionStorage = systemDescriptionStorage;
        this.systemStructureCommandRunner = systemStructureCommandRunner;
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

}
