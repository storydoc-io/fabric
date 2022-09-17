package io.storydoc.fabric.systemdescription.infra.jsonmodel;

import io.storydoc.fabric.workspace.domain.WorkspaceResource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SystemDescription implements WorkspaceResource {
    private List<SystemComponent> systemComponents;
    private List<Environment> environments;
    private Map<String, Map<String, Map<String, String>>> settings;
}
