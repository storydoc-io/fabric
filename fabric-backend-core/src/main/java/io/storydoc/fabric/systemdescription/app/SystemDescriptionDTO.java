package io.storydoc.fabric.systemdescription.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SystemDescriptionDTO {
    private List<SystemComponentDTO> systemComponents;
    private List<EnvironmentDTO> environments;
    private Map<String, Map<String, Map<String, String>>> settings;
}
