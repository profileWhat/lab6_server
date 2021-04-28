package serverManagementModule;

/**
 * Exception class that is thrown when trying to execute a script that is already running
 */
public class ReuseExecuteScriptException extends RuntimeException {
    public ReuseExecuteScriptException() {
        super("Try to reuse executing script, reuse execution aborted ");
    }
}
