package io.storydoc.fabric.command.app;

import io.storydoc.fabric.TestBase;
import io.storydoc.fabric.command.app.dummy.DummyCommand1;
import io.storydoc.fabric.command.app.dummy.DummyCommand2;
import io.storydoc.fabric.command.domain.Command;
import io.storydoc.fabric.command.domain.ExecutionId;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CommandExecutionServiceTest extends TestBase {

    @Autowired
    CommandService commandService;

    @Test
    public void create() {
        Command command = new DummyCommand1(
                List.of(
                    new DummyCommand2(10),
                    new DummyCommand2(15)
                ));

        ExecutionId executionId =  commandService.run(command);

        int maxTimeEstim = Math.max(10, 15) * 200 ;
        int lapTime = 500;
        int lapCount = maxTimeEstim/ lapTime + 2;

        sleep(50);
        for (int lap = 0; lap < lapCount; lap++) {
            log.info("lap: " + lap + " " + commandService.getContextInfo(executionId));
            sleep(lapTime);
        }


    }

    private void sleep(int milliSeconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliSeconds);
        } catch (InterruptedException e) {
        }
    }

}