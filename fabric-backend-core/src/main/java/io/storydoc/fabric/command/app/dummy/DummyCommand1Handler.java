package io.storydoc.fabric.command.app.dummy;

import io.storydoc.fabric.command.domain.CommandExecutionEngine;
import io.storydoc.fabric.command.domain.CommandHandler;
import io.storydoc.fabric.command.domain.CommandType;
import io.storydoc.fabric.command.domain.ExecutionContext;
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
    public void run(DummyCommand1 command, ExecutionContext context, CommandExecutionEngine engine) {
        log.info("begin running " + command);
        for (DummyCommand2 subCommand: command.getSubCommands()) {
            engine.runSubCommand(subCommand, context);
        }
        log.info("end running " + command);
    }
}
