package io.storydoc.fabric.command.app;

import io.storydoc.fabric.command.app.dummy.DummyCommand1;
import io.storydoc.fabric.command.app.dummy.DummyCommand2;
import io.storydoc.fabric.command.app.dummy.DummyCommandTypes;
import io.storydoc.fabric.command.domain.Command;
import io.storydoc.fabric.command.domain.ExecutionId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
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

   // @PostMapping(value="/dummy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExecutionId dummy() {
        Command<DummyCommand1> command = new Command.Builder<DummyCommand1>()
                .name("dummy parent command")
                .commandType(DummyCommandTypes.DUMMY_COMMAND_TYPE_1)
                .params(new DummyCommand1())
                .children(List.of(
                        new Command.Builder<DummyCommand2>()
                                .name("dummy child command 1")
                                .commandType(DummyCommandTypes.DUMMY_COMMAND_TYPE_2)
                                .params(new DummyCommand2(10))
                                .build(),
                        new Command.Builder<DummyCommand2>()
                                .name("dummy child command 2")
                                .commandType(DummyCommandTypes.DUMMY_COMMAND_TYPE_2)
                                .params(new DummyCommand2(20))
                                .build()
                                ))
                .build();

        return  commandService.run(command);
    }

    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExecutionDTO getExecutionInfo(ExecutionId executionId) {
        return commandService.getContextInfo(executionId);
    }

}
