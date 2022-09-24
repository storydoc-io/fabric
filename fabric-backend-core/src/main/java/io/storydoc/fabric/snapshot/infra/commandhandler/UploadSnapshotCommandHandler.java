package io.storydoc.fabric.snapshot.infra.commandhandler;

import io.storydoc.fabric.command.domain.CommandExecutionEngine;
import io.storydoc.fabric.command.domain.CommandHandler;
import io.storydoc.fabric.command.domain.CommandType;
import io.storydoc.fabric.command.domain.ExecutionContext;
import io.storydoc.fabric.snapshot.domain.upload.UploadCommandTypes;
import io.storydoc.fabric.snapshot.domain.upload.UploadSnapshotCommand;
import io.storydoc.fabric.snapshot.domain.upload.UploadSnapshotComponentCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UploadSnapshotCommandHandler extends CommandHandler<UploadSnapshotCommand> {

    @Override
    public CommandType getType() {
        return UploadCommandTypes.SNAPSHOT;
    }


    @Override
    public void run(UploadSnapshotCommand command, ExecutionContext context, CommandExecutionEngine engine) {
        log.info("begin running " + command);
        for (UploadSnapshotComponentCommand subCommand: command.getSubCommands()) {
            engine.runSubCommand(subCommand, context);
        }
        log.info("end running " + command);
    }



}
