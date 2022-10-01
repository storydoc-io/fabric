package io.storydoc.fabric.snapshot.infra.commandhandler;

import io.storydoc.fabric.command.domain.*;
import io.storydoc.fabric.snapshot.domain.upload.UploadCommandTypes;
import io.storydoc.fabric.snapshot.domain.upload.UploadSnapshotComponentCommandParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class UploadSnapshotComponentCommandHandler extends CommandHandler<UploadSnapshotComponentCommandParams> {

    @Override
    public CommandType getType() {
        return UploadCommandTypes.SNAPSHOT_COMPONENT;
    }


    @Override
    public void run(Command<UploadSnapshotComponentCommandParams> command, ExecutionContext context, CommandExecutionEngine engine) {
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
