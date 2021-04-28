package serverManagementModule;

/**
 * Exception class that is thrown when the command and its number of arguments are entered incorrectly
 */
public class WrongCommandException extends RuntimeException {
    public WrongCommandException(String message) {
        super(message);
    }
}
