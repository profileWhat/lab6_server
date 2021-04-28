package commands;

import java.io.Serializable;

/**
 * Class of the command received from the client
 */
public class ReceivedCommand implements Serializable {
    private static final long serialVersionUID = 1L;
    private final CommandName commandName;
    private final Object argument;

    /**
     * Constructor for set commandName and argument of command
     * @param commandName to set in object
     * @param argument to set in object
     */
    protected ReceivedCommand(CommandName commandName, Object argument) {
        this.commandName = commandName;
        this.argument = argument;
    }

    /**
     * Method for get argument of command
     * @return argument of command
     */
    public Object getArgument() {
        return argument;
    }

    /**
     * Method for get command name
     * @return command name
     */
    public CommandName getCommandName() {
        return commandName;
    }
}
