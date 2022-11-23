package io.storydoc.fabric.console.app;

import io.storydoc.fabric.TestBase;
import io.storydoc.fabric.console.app.snippet.SnippetDTO;
import io.storydoc.fabric.core.infra.WorkspaceStructure;
import io.storydoc.fabric.workspace.WorkspaceTestFixture;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Slf4j
public class ConsoleServiceTest extends TestBase {

    @Autowired
    ConsoleService consoleService;

    @Autowired
    WorkspaceTestFixture workspaceTestFixture;

    @Autowired
    WorkspaceStructure workspaceStructure;

    @Test
    public void create_snippet() {
        // given a system type
        String systemType = "SYSTEMTYPE";

        // when I add a snippet
        SnippetDTO snippetDTO = SnippetDTO.builder()
                .attributes(Map.of(
                "key1", "value1_",
                "key2", "value2_"
                ))
                .title("title_")
                .build();
        consoleService.createSnippet(systemType, snippetDTO);

        workspaceTestFixture.logFolderStructure("after create");
        workspaceTestFixture.logResourceContent("snippets file", workspaceStructure.getSnippetsUrn(systemType));

        // then the snippet is in the snippet list
        List<SnippetDTO> snippets = consoleService.getSnippets(systemType);
        assertNotNull(snippets);
        assertEquals(1, snippets.size());

        SnippetDTO snippetDTO1 = snippets.get(0);
        assertNotNull(snippetDTO1.getId());

    }

    @Test
    public void edit_snippet() {
        // given a system type
        String systemType = "SYSTEMTYPE";

        // and a snippet
        SnippetDTO snippetDTO_before = SnippetDTO.builder()
                .attributes(Map.of(
                        "key1", "value1_before",
                        "key2", "value2_before"
                ))
                .title("title_before")
                .build();
        String id = consoleService.createSnippet(systemType, snippetDTO_before);

        // when I edit the snippet
        SnippetDTO snippetDTO_edited = SnippetDTO.builder()
                .id(id)
                .attributes(Map.of(
                        "key1", "value1_edited",
                        "key2", "value2_edited"
                ))
                .title("title_edited")
                .build();

        consoleService.editSnippet(systemType, snippetDTO_edited);

        workspaceTestFixture.logFolderStructure("after create");
        workspaceTestFixture.logResourceContent("snippets file", workspaceStructure.getSnippetsUrn(systemType));

        // then the snippet is updated
        List<SnippetDTO> snippets = consoleService.getSnippets(systemType);
        assertNotNull(snippets);
        assertEquals(1, snippets.size());

        SnippetDTO snippetDTO1 = snippets.get(0);
        assertEquals(id, snippetDTO1.getId());
        assertEquals("title_edited", snippetDTO1.getTitle());
        assertEquals("value1_edited", snippetDTO1.getAttributes().get("key1"));

    }

    @Test
    public void delete_snippet() {
        // given a system type
        String systemType = "SYSTEMTYPE";

        // and a snippet
        SnippetDTO snippetDTO_before = SnippetDTO.builder()
                .attributes(Map.of(
                        "key1", "value1_before",
                        "key2", "value2_before"
                ))
                .title("title_before")
                .build();
        String id = consoleService.createSnippet(systemType, snippetDTO_before);

        // when I delete the snippet
        consoleService.deleteSnippet(systemType, id);

        workspaceTestFixture.logFolderStructure("after delete");
        workspaceTestFixture.logResourceContent("snippets file", workspaceStructure.getSnippetsUrn(systemType));

        // then the snippet is updated
        List<SnippetDTO> snippets = consoleService.getSnippets(systemType);
        assertNotNull(snippets);
        assertEquals(0, snippets.size());


    }

}