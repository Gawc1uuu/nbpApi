package pl.kurs.nbp.exceptions;

public class EmptyListException extends RuntimeException{
    public EmptyListException() {
    }

    public EmptyListException(String message) {
        super(message);
    }
}
