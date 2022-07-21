package zw.co.codehive.userservice.exceptions;

public class UserNameAlreadyExistsException extends RuntimeException {

    public UserNameAlreadyExistsException() {
        super();
    }

    public UserNameAlreadyExistsException(String message) {
        super(message);
    }

    public UserNameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNameAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    protected UserNameAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
