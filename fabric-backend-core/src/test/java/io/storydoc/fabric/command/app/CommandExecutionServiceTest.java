package io.storydoc.fabric.command.app;

import io.storydoc.fabric.TestBase;
import io.storydoc.fabric.command.app.dummy.DummyCommand1;
import io.storydoc.fabric.command.app.dummy.DummyCommand2;
import io.storydoc.fabric.command.domain.Command;
import io.storydoc.fabric.command.domain.ExecutionId;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CommandExecutionServiceTest extends TestBase {

    @Test
    public void create() {

        int RECORD_COUNT_1 = 2;
        int RECORD_COUNT_2 = 3;

        Command<DummyCommand1> command = new Command.Builder<DummyCommand1>()
                .name("dummy parent command")
                .params(new DummyCommand1())
                .children(List.of(
                        new Command.Builder<DummyCommand2>()
                                .name("dummy child command 1")
                                .params(new DummyCommand2(RECORD_COUNT_1))
                                .run(this::run)
                                .build(),
                        new Command.Builder<DummyCommand2>()
                                .name("dummy child command 2")
                                .params(new DummyCommand2(RECORD_COUNT_2))
                                .run(this::run)
                                .build()
                ))
                .build();

        ExecutionId executionId =  commandService.run(command);

        waitUntilExecutionFinished(executionId);

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


}