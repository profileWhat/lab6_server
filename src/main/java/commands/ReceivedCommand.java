package commands;

import java.io.Serializable;

public class ReceivedCommand implements Serializable {
    private static final long serialVersionUID = 1L;
    private CommandName commandsName;
    private Object argument;

    protected ReceivedCommand(CommandName commandsName, Object argument) {
        this.commandsName = commandsName;
        this.argument = argument;
    }

    public Object getArgument() {
        return argument;
    }

    public CommandName getCommandName() {
        return commandsName;
    }
}
