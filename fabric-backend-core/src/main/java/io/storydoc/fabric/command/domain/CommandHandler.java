package io.storydoc.fabric.command.domain;

@FunctionalInterface
public interface CommandHandler<PARAMS extends CommandParams> {

    void exceute(Command<PARAMS> command);

}
