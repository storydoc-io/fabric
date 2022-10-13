package io.storydoc.fabric.command.domain;


import io.storydoc.fabric.command.app.ExecutionStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Command<PARAMS extends CommandParams> implements ProgressMonitor {

    private final PARAMS params;

    private final String name;

    private Command<? extends CommandParams> parent;

    private final CommandHandler<PARAMS> run;

    private final List<Command<? extends CommandParams>> children;

    private ExecutionStatus status;

    private int percentDone = 0;

    private Exception exception;

    public PARAMS getParams() {
        return params;
    }

    public String getName() {
        return name;
    }

    public List<Command<? extends CommandParams>> getChildren() {
        return children;
    }

    private Command(String name, List<Command<? extends CommandParams>> children, PARAMS params, CommandHandler<PARAMS> run) {
        this.name = name;
        this.children = children;
        this.run = run;
        if (this.children != null) {
            this.children.forEach(childCommand -> childCommand.parent = this);
        }
        this.params = params;
        status = ExecutionStatus.READY;
    }

    public Command getParent() {
        return parent;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatus status) {
        log.trace(String.format("%s : %s -> %s", name, this.status, status));
        this.status = status;
    }

    public int getPercentDone() {
        return percentDone;
    }

    public void setPercentDone(int percentDone) {
        log.trace(String.format("%s : %d%%", name, percentDone));
        this.percentDone = percentDone;
    }

    public CommandHandler<PARAMS> getRun() {
        return run;
    }

    static public Builder<? extends CommandParams> builder() {
        return new Builder<>();
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    static public class Builder<PARAMS extends CommandParams> {
        private List<Command<? extends CommandParams>> children;
        private String name;
        private PARAMS params;
        private CommandHandler<PARAMS> run;

        public Builder<PARAMS> name(String name) {
            this.name = name;
            return this;
        }

        public Builder<PARAMS> children(List<Command<? extends CommandParams>> children) {
            this.children = children;
            return this;
        }

        public Builder<PARAMS> params(PARAMS params) {
            this.params = params;
            return this;
        }

        public Builder<PARAMS> run(CommandHandler<PARAMS> run) {
            this.run = run;
            return this;
        }

        public Command<PARAMS> build() {
            return new Command<PARAMS>(name, children, params, run);
        }

    }
}
