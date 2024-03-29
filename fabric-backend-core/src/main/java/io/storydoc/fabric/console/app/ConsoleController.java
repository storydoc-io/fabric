package io.storydoc.fabric.console.app;

import io.storydoc.fabric.console.app.describe.ConsoleDescriptorDTO;
import io.storydoc.fabric.console.app.navigation.NavItem;
import io.storydoc.fabric.console.app.navigation.NavigationRequest;
import io.storydoc.fabric.console.app.snippet.SnippetDTO;
import io.storydoc.fabric.query.app.QueryDTO;
import io.storydoc.fabric.query.app.ResultDTO;
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
    public ResultDTO runRequest(@RequestBody QueryDTO queryDTO) {
        return consoleService.runRequest(queryDTO);
    }

    @PostMapping(value = "/snippet", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createSnippet(String systemType, @RequestBody SnippetDTO snippetDTO) {
        consoleService.createSnippet(systemType, snippetDTO);
    }

    @PutMapping(value = "/snippet", produces = MediaType.APPLICATION_JSON_VALUE)
    public void updateSnippet(String systemType, @RequestBody SnippetDTO snippetDTO) {
        consoleService.editSnippet(systemType, snippetDTO);
    }

    @DeleteMapping(value = "/snippet", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteSnippet(String systemType, String id) {
        consoleService.deleteSnippet(systemType, id);
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
