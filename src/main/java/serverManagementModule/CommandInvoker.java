package serverManagementModule;

import java.util.HashMap;

import commands.Command;
import commands.CommandName;

/**
 * Invoker class
 * <p>
 * Class for invoke specific commands
 */
public class CommandInvoker {
    private final HashMap<CommandName, Command> commandMap = new HashMap<>();

    /**
     * Method for execute Command
     *
     * @param commandName to invoke command by name
     * @param object        to set argument of command
     */
    public void execute(CommandName commandName, Object object) {
        Command command = commandMap.get(commandName);
        command.execute(object);
    }

    /**
     * Method for register Command
     *
     * @param commandName to register command name
     * @param command     to register command class
     */
    public void register(CommandName commandName, Command command) {
        commandMap.put(commandName, command);
    }

    /**
     * Method for return command Map
     * @return command Map
     */
    public HashMap<CommandName, Command> getCommandMap() {
        return commandMap;
    }

}
