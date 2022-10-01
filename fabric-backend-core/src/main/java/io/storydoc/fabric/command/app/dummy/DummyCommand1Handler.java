package io.storydoc.fabric.command.app.dummy;

import io.storydoc.fabric.command.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DummyCommand1Handler extends CommandHandler<DummyCommand1> {

    @Override
    public CommandType getType() {
        return DummyCommandTypes.DUMMY_COMMAND_TYPE_1;
    }


    @Override
    public void run(Command<DummyCommand1> command, ExecutionContext context, CommandExecutionEngine engine) {
        command.getParams();
    }

}
