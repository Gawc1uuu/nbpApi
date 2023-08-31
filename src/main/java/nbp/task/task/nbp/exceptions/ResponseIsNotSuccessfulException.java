package nbp.task.task.nbp.exceptions;

public class ResponseIsNotSuccessfulException extends RuntimeException {
    public ResponseIsNotSuccessfulException() {
    }

    public ResponseIsNotSuccessfulException(String message) {
        super(message);
    }
}
