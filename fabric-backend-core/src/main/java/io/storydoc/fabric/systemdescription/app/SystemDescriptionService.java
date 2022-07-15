package io.storydoc.fabric.systemdescription.app;

import io.storydoc.fabric.systemdescription.domain.SystemDescriptionStorage;
import io.storydoc.fabric.systemdescription.infra.jsonmodel.SystemDescription;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
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
                .settings(toDto(systemDescription.getSettings()))
                .build();
    }

    private Map<String, Map<String, Map<String, String>>> toDto(Map<String, Map<String, Map<String, String>>> settings) {
        Map<String, Map<String, Map<String, String>>> dto = new HashMap<>();
        for(String envKey: settings.keySet()) {
            Map<String, Map<String, String>> envSettings = settings.get(envKey);
            Map<String, Map<String, String>> envSettingsDto = new HashMap<>(envSettings);
            dto.put(envKey, envSettingsDto);
        }
        return dto;
    }


}
