package io.storydoc.fabric.systemdescription.app;

import io.storydoc.fabric.connection.app.ConnectionTestRequestDTO;
import io.storydoc.fabric.connection.app.ConnectionTestResponseDTO;
import io.storydoc.fabric.systemdescription.domain.SystemDescriptionStorage;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.Environment;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemComponent;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemDescription;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SystemDescriptionService {

    private final SystemDescriptionStorage systemDescriptionStorage;

    public SystemDescriptionService(SystemDescriptionStorage systemDescriptionStorage) {
        this.systemDescriptionStorage = systemDescriptionStorage;
    }

    public SystemDescriptionDTO getSystemDescription() {
        SystemDescription systemDescription = systemDescriptionStorage.getOrCreateSystemDescription();
        return SystemDescriptionDTO.builder()
                .systemComponents(systemDescription.getSystemComponents().stream()
                        .map(systemComponent -> SystemComponentDTO.builder()
                                .key(systemComponent.getKey())
                                .label(systemComponent.getLabel())
                                .systemType(systemComponent.getSystemType())
                                .build())
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

}
