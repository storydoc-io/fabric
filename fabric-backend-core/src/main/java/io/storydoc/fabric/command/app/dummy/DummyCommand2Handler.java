package io.storydoc.fabric.command.app.dummy;

import io.storydoc.fabric.command.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class DummyCommand2Handler extends CommandHandler<DummyCommand2> {

    @Override
    public CommandType getType() {
        return DummyCommandTypes.DUMMY_COMMAND_TYPE_2;
    }

    @Override
    public void run(Command<DummyCommand2> command, ExecutionContext context, CommandExecutionEngine commandExecutionEngine) {
        log.info("begin running " + command);

        sleep(200);
        context.setPercentDone(0);
        for (int i = 0; i < command.getParams().getRecordCount(); i++) {
            sleep(200);
            context.setPercentDone(100 * (i+1) / command.getParams().getRecordCount());
        }

        log.info("end running " + command);
    }

    private void sleep(int amount) {
        try {
            TimeUnit.MILLISECONDS.sleep(amount);
        } catch (InterruptedException e) {
        }
    }
}
