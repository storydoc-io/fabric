package io.storydoc.fabric.command.app;

import io.storydoc.fabric.command.app.dummy.DummyCommand1;
import io.storydoc.fabric.command.app.dummy.DummyCommand2;
import io.storydoc.fabric.command.domain.Command;
import io.storydoc.fabric.command.domain.ExecutionId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metamodel")
public class CommandController {

    private final CommandService commandService;

    public CommandController(CommandService commandService) {
        this.commandService = commandService;
    }

    @PostMapping(value="/dummy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExecutionId dummy() {
        Command command = new DummyCommand1(
                List.of(
                        new DummyCommand2(100),
                        new DummyCommand2(150)
                ));

        return  commandService.run(command);
    }

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExecutionDTO getExecutionInfo(ExecutionId executionId) {
        return commandService.getContextInfo(executionId);
    }

}
