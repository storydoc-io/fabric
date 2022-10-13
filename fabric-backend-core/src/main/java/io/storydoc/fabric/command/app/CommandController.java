package io.storydoc.fabric.command.app;

import io.storydoc.fabric.command.app.dummy.DummyCommand1;
import io.storydoc.fabric.command.app.dummy.DummyCommand2;
import io.storydoc.fabric.command.domain.Command;
import io.storydoc.fabric.command.domain.ExecutionId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

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
                .params(new DummyCommand1())
                .children(List.of(
                        new Command.Builder<DummyCommand2>()
                                .name("dummy child command 1")
                                .params(new DummyCommand2(10))
                                .run(this::run)
                                .build(),
                        new Command.Builder<DummyCommand2>()
                                .name("dummy child command 2")
                                .params(new DummyCommand2(20))
                                .run(this::run)
                                .build()
                                ))
                .build();

        return  commandService.run(command);
    }

    void run(Command<DummyCommand2> command) {
        sleep(200);
        command.setPercentDone(0);
        for (int i = 0; i < command.getParams().getRecordCount(); i++) {
            sleep(200);
            command.setPercentDone(100 * (i+1) / command.getParams().getRecordCount());
        }
    }

    private void sleep(int amount) {
        try {
            TimeUnit.MILLISECONDS.sleep(amount);
        } catch (InterruptedException e) {
        }
    }


    @GetMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExecutionDTO getExecutionInfo(ExecutionId executionId) {
        return commandService.getContextInfo(executionId);
    }

}
