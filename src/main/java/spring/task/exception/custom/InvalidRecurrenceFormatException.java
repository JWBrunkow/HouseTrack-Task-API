package spring.task.exception.custom;

public class InvalidRecurrenceFormatException extends RuntimeException {

    public InvalidRecurrenceFormatException(String message) {
        super(message);
    }

    public InvalidRecurrenceFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
