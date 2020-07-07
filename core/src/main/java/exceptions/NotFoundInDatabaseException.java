package exceptions;

public class NotFoundInDatabaseException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotFoundInDatabaseException(String message) {
        super(message);
    }
}
