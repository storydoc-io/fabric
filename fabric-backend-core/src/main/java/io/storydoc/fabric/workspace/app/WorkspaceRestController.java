package io.storydoc.fabric.workspace.app;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/settings")
public class WorkspaceRestController {

    private final WorkspaceSettings workspaceSettings;

    public WorkspaceRestController(WorkspaceSettings workspaceSettings) {
        this.workspaceSettings = workspaceSettings;
    }

    @GetMapping(value = "/settings", produces = MediaType.APPLICATION_JSON_VALUE)
    public WorkspaceSettings getSettings() {
        return workspaceSettings;
    }


}
