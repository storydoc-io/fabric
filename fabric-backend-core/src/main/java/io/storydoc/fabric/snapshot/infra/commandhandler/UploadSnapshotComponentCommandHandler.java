package io.storydoc.fabric.snapshot.infra.commandhandler;

import io.storydoc.fabric.command.domain.CommandExecutionEngine;
import io.storydoc.fabric.command.domain.CommandHandler;
import io.storydoc.fabric.command.domain.CommandType;
import io.storydoc.fabric.command.domain.ExecutionContext;
import io.storydoc.fabric.snapshot.domain.upload.UploadCommandTypes;
import io.storydoc.fabric.snapshot.domain.upload.UploadSnapshotComponentCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class UploadSnapshotComponentCommandHandler extends CommandHandler<UploadSnapshotComponentCommand> {

    @Override
    public CommandType getType() {
        return UploadCommandTypes.SNAPSHOT_COMPONENT;
    }


    @Override
    public void run(UploadSnapshotComponentCommand command, ExecutionContext context, CommandExecutionEngine engine) {
        log.info("begin running " + command);

        sleep(200);
        context.setPercentDone(0);
        for (int i = 0; i < command.getRecordCount(); i++) {
            sleep(200);
            context.setPercentDone(100 * (i+1) / command.getRecordCount());
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
