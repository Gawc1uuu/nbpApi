package nbp.task.task.nbp.exceptions;

public class CannotExtractDataFromResponseBodyException extends RuntimeException {
    public CannotExtractDataFromResponseBodyException() {
    }

    public CannotExtractDataFromResponseBodyException(String message) {
        super(message);
    }
}
