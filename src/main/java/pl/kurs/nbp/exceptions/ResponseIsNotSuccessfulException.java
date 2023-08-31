package pl.kurs.nbp.task.exceptions;

public class ResponseIsNotSuccessfulException extends RuntimeException {
    public ResponseIsNotSuccessfulException() {
    }

    public ResponseIsNotSuccessfulException(String message) {
        super(message);
    }
}
