package io.storydoc.fabric.console.app;

import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.navigation.NavItem;
import io.storydoc.fabric.console.app.navigation.NavigationRequest;
import io.storydoc.fabric.console.app.query.ConsoleRequestDTO;
import io.storydoc.fabric.console.app.query.ConsoleResponseItemDTO;
import io.storydoc.fabric.console.app.snippet.SnippetDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/console")
public class ConsoleController {

    private final ConsoleService consoleService;

    public ConsoleController(ConsoleService consoleService) {
        this.consoleService = consoleService;
    }

    @GetMapping(value= "/descriptor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ConsoleDescriptorDTO getDescriptor(String systemType) {
        return consoleService.getDescriptor(systemType);
    }

    @PostMapping(value="/run", produces = MediaType.APPLICATION_JSON_VALUE)
    public ConsoleResponseItemDTO runRequest(@RequestBody ConsoleRequestDTO consoleRequestDTO) {
        return consoleService.runRequest(consoleRequestDTO);
    }

    @PostMapping(value = "/snippet", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createSnippet(String systemType, @RequestBody SnippetDTO snippetDTO) {
        consoleService.createSnippet(systemType, snippetDTO);
    }

    @GetMapping(value="/snippets", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SnippetDTO> getSnippets(String systemType) {
        return consoleService.getSnippets(systemType);
    }

    @PostMapping(value="/navigation", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NavItem> getNavigation(@RequestBody NavigationRequest navigationRequest) {
        return consoleService.getNavigation(navigationRequest);
    }

}
