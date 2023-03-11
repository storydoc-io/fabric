package io.storydoc.fabric.console.app;

import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.navigation.NavItem;
import io.storydoc.fabric.console.app.navigation.NavigationRequest;
import io.storydoc.fabric.console.app.snippet.SnippetDTO;
import io.storydoc.fabric.console.domain.ConsoleHandler;
import io.storydoc.fabric.console.domain.ConsoleHandlerRegistry;
import io.storydoc.fabric.console.domain.SnippetStorage;
import io.storydoc.fabric.console.infra.Snippet;
import io.storydoc.fabric.query.app.QueryDTO;
import io.storydoc.fabric.query.app.ResultDTO;
import io.storydoc.fabric.query.app.composite.QueryCompositeDTO;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ConsoleService {

    private final ConsoleHandlerRegistry handlerRegistry;
    private final SystemDescriptionService systemDescriptionService;
    private final SnippetStorage snippetStorage;

    public ConsoleService(ConsoleHandlerRegistry handlerRegistry, SystemDescriptionService systemDescriptionService, SnippetStorage snippetStorage) {
        this.handlerRegistry = handlerRegistry;
        this.systemDescriptionService = systemDescriptionService;
        this.snippetStorage = snippetStorage;
    }

    private SystemComponentDTO getSystemComponentDTO(String systemComponentKey) {
        return systemDescriptionService.getSystemComponentDTO(systemComponentKey);
    }

    private ConsoleHandler getHandler(String systemType) {
        return handlerRegistry.getHandler(systemType);
    }

    public QueryCompositeDTO runRequest(QueryCompositeDTO composite) {
        QueryDTO query = composite.getQuery();
        SystemComponentDTO systemComponentDTO = getSystemComponentDTO(query.getSystemComponentKey());
        Map<String, String> settings = systemDescriptionService.getSettings(query.getSystemComponentKey(), query.getEnvironmentKey());
        ResultDTO result = getHandler(systemComponentDTO.getSystemType()).runRequest(query, settings);
        return QueryCompositeDTO.builder()
                .id(composite.getId())
                .query(query)
                .result(result)
                .build();
    }


    public ConsoleDescriptorDTO getDescriptor(String systemType) {
        return getHandler(systemType).getDescriptor();
    }

    public List<SnippetDTO> getSnippets(String systemType) {
        return snippetStorage.getSnippets(systemType).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private SnippetDTO toDto(Snippet snippet) {
        return SnippetDTO.builder()
                .id(snippet.getId())
                .title(snippet.getTitle())
                .attributes(snippet.getAttributes())
                .build();
    }

    public String createSnippet(String systemType, SnippetDTO snippetDTO) {
        String id = UUID.randomUUID().toString();
        snippetDTO.setId(id);
        snippetStorage.addSnippet(systemType, Snippet.builder()
                .id(snippetDTO.getId())
                .title(snippetDTO.getTitle())
                .attributes(snippetDTO.getAttributes())
                .build()
        );
        return id;
    }

    public void editSnippet(String systemType, SnippetDTO snippetDTO) {
        snippetStorage.updateSnippet(systemType, Snippet.builder()
                .id(snippetDTO.getId())
                .title(snippetDTO.getTitle())
                .attributes(snippetDTO.getAttributes())
                .build());
    }

    public void deleteSnippet(String systemType, String id) {
        snippetStorage.deleteSnippet(systemType, id);
    }

    public List<NavItem> getNavigation(NavigationRequest navigationRequest) {
        SystemComponentDTO systemComponentDTO = getSystemComponentDTO(navigationRequest.getSystemComponentKey());
        return getHandler(systemComponentDTO.getSystemType()).getNavigation(navigationRequest);
    }
}
