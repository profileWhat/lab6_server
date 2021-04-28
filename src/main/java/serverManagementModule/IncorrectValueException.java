package serverManagementModule;

/**
 * Exception class that is thrown when the primitive types for the complex argument are entered incorrectly
 */
public class IncorrectValueException extends Exception {
    public IncorrectValueException(String message) {
        super(message);
    }
}
