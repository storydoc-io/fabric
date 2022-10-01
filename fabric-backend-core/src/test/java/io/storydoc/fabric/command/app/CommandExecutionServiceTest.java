package io.storydoc.fabric.command.app;

import io.storydoc.fabric.TestBase;
import io.storydoc.fabric.command.app.dummy.DummyCommand1;
import io.storydoc.fabric.command.app.dummy.DummyCommand2;
import io.storydoc.fabric.command.app.dummy.DummyCommandTypes;
import io.storydoc.fabric.command.domain.Command;
import io.storydoc.fabric.command.domain.ExecutionId;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
public class CommandExecutionServiceTest extends TestBase {

    @Autowired
    CommandService commandService;

    @Test
    @Ignore
    public void create() {

        int RECORD_COUNT_1 = 2;
        int RECORD_COUNT_2 = 3;

        Command<DummyCommand1> command = new Command.Builder<DummyCommand1>()
                .name("dummy parent command")
                .commandType(DummyCommandTypes.DUMMY_COMMAND_TYPE_1)
                .params(new DummyCommand1())
                .children(List.of(
                        new Command.Builder<DummyCommand2>()
                                .name("dummy child command 1")
                                .commandType(DummyCommandTypes.DUMMY_COMMAND_TYPE_2)
                                .params(new DummyCommand2(RECORD_COUNT_1))
                                .build(),
                        new Command.Builder<DummyCommand2>()
                                .name("dummy child command 2")
                                .commandType(DummyCommandTypes.DUMMY_COMMAND_TYPE_2)
                                .params(new DummyCommand2(RECORD_COUNT_2))
                                .build()
                ))
                .build();

        ExecutionId executionId =  commandService.run(command);

        int maxTimeEstim = Math.max(RECORD_COUNT_1, RECORD_COUNT_2) * 200 ;

        await()
            .atMost(maxTimeEstim, TimeUnit.MILLISECONDS)
            .failFast("command ended with error", ()-> ExecutionStatus.ERROR.equals(commandService.getContextInfo(executionId).getStatus()))
            .until( () -> commandService.getContextInfo(executionId).getStatus(), equalTo(ExecutionStatus.DONE))
            ;

    }


}