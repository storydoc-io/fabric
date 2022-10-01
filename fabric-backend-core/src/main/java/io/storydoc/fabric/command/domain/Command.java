package io.storydoc.fabric.command.domain;


import io.storydoc.fabric.command.app.ExecutionStatus;

import java.util.List;

public class Command<PARAMS extends CommandParams> {

    private final PARAMS params;

    private final String name;

    private Command<? extends CommandParams> parent;

    private final CommandType commandType;

    private final List<Command<? extends CommandParams>> children;

    private ExecutionStatus status;

    public PARAMS getParams() {
        return params;
    }

    public String getName() {
        return name;
    }

    public List<Command<? extends CommandParams>> getChildren() {
        return children;
    }

    private Command(String name, CommandType commandType, List<Command<? extends CommandParams>> children, PARAMS params) {
        this.name = name;
        this.commandType = commandType;
        this.children = children;
        this.children.forEach(childCommand -> childCommand.setParent(this));
        this.params = params;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public Command getParent() {
        return parent;
    }

    public void setParent(Command parent) {
        this.parent = parent;
    }

    public ExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(ExecutionStatus status) {
        this.status = status;
    }

    static public Builder<? extends CommandParams> builder() {
        return new Builder<>();
    }

    static public class Builder<PARAMS extends CommandParams> {
        private List<Command<? extends CommandParams>> children;
        private  CommandType commandType;
        private String name;
        private PARAMS params;

        public Builder<PARAMS> commandType(CommandType commandType) {
            this.commandType = commandType;
            return this;
        }

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

        public Command<PARAMS> build() {
            return new Command<PARAMS>(name, commandType, children, params);
        }

    }
}
