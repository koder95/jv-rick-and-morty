package mate.academy.rickandmorty.exception;

public class InvalidConnectionException extends RuntimeException {
    public InvalidConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
