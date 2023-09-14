package spring.task.exception.custom;

public class InvalidTaskInputException extends RuntimeException {
    public InvalidTaskInputException(String message) {
        super(message);
    }
}
