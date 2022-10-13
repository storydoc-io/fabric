package io.storydoc.fabric.console.infra;

import com.fasterxml.jackson.core.type.TypeReference;
import io.storydoc.fabric.console.domain.SnippetStorage;
import io.storydoc.fabric.core.infra.StorageBase;
import io.storydoc.fabric.core.infra.WorkspaceStructure;
import io.storydoc.fabric.workspace.app.WorkspaceQueryService;
import io.storydoc.fabric.workspace.app.WorkspaceService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SnippetStorageImpl extends StorageBase implements SnippetStorage {

    private final WorkspaceStructure workspaceStructure;

    private final WorkspaceQueryService workspaceQueryService;

    private final WorkspaceService workspaceService;

    public SnippetStorageImpl(WorkspaceStructure workspaceStructure, WorkspaceQueryService workspaceQueryService, WorkspaceService workspaceService) {
        this.workspaceStructure = workspaceStructure;
        this.workspaceQueryService = workspaceQueryService;
        this.workspaceService = workspaceService;
    }

    @Override
    public void addSnippet(String systemType, Snippet snippet) {
         List<Snippet> snippets  = getSnippets(systemType);
         snippets.add(snippet);
         saveSnippets(systemType, snippets);
    }

    @SneakyThrows
    private void saveSnippets(String systemType, List<Snippet> snippets) {
        workspaceService.saveResource(workspaceStructure.getSnippetsUrn(systemType), os -> objectMapper.writeValue(os, snippets));
    }

    @Override
    @SneakyThrows
    public List<Snippet> getSnippets(String systemType) {
        try {
            return objectMapper.readValue(workspaceQueryService.getInputStream(workspaceStructure.getSnippetsUrn(systemType)), new TypeReference<List<Snippet>>() {
            });
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }

}
