package io.storydoc.fabric.console.app;

import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.snippet.SnippetDTO;
import io.storydoc.fabric.console.domain.ConsoleHandler;
import io.storydoc.fabric.console.domain.ConsoleHandlerRegistry;
import io.storydoc.fabric.console.domain.SnippetStorage;
import io.storydoc.fabric.console.infra.Snippet;
import io.storydoc.fabric.systemdescription.app.SystemComponentDTO;
import io.storydoc.fabric.systemdescription.app.SystemDescriptionService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
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

    private ConsoleHandler getHandler(SystemComponentDTO systemComponentDTO) {
        return handlerRegistry.getHandler(systemComponentDTO.getSystemType());
    }

    public ConsoleResponseItemDTO runRequest(ConsoleRequestDTO request) {
        SystemComponentDTO systemComponentDTO = getSystemComponentDTO(request.getSystemComponentKey());
        Map<String, String> settings = systemDescriptionService.getSettings(request.getSystemComponentKey(), request.getEnvironmentKey());
        return getHandler(systemComponentDTO).runRequest(request, settings);
    }


    public ConsoleDescriptorDTO getDescriptor(String systemComponentKey) {
        SystemComponentDTO systemComponentDTO = getSystemComponentDTO(systemComponentKey);
        return getHandler(systemComponentDTO).getDescriptor();
    }

    public List<SnippetDTO> getSnippets(String systemComponentKey) {
        String systemType =  getSystemComponentDTO(systemComponentKey).getSystemType();
        return snippetStorage.getSnippets(systemType).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private SnippetDTO toDto(Snippet snippet) {
        return SnippetDTO.builder()
                .title(snippet.getTitle())
                .attributes(snippet.getAttributes())
                .build();
    }

    public void createSnippet(String systemComponentKey, SnippetDTO snippetDTO) {
        String systemType =  getSystemComponentDTO(systemComponentKey).getSystemType();
        snippetStorage.addSnippet(systemType, Snippet.builder()
                .title(snippetDTO.getTitle())
                .attributes(snippetDTO.getAttributes())
                .build()
        );
    }
}
