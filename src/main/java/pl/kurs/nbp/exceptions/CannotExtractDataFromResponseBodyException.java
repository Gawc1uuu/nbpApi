package pl.kurs.nbp.exceptions;

public class CannotExtractDataFromResponseBodyException extends RuntimeException {
    public CannotExtractDataFromResponseBodyException() {
    }

    public CannotExtractDataFromResponseBodyException(String message) {
        super(message);
    }
}
