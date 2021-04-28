package commands;

/**
 * Interface command, which defines the base of the command
 */
public interface Command {
    /**
     * Abstract Method for execute Command
     *
     * @param object for set argument
     */
    void execute(Object object);

    /**
     * Abstract Method for describe Command
     */
    void describe();

}
