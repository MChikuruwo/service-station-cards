package zw.co.jugaad.userservice.exceptions;

public class UserAuthenticationErrorException extends RuntimeException {

    public UserAuthenticationErrorException() {
        super();
    }

    public UserAuthenticationErrorException(String message) {
        super(message);
    }

    public UserAuthenticationErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAuthenticationErrorException(Throwable cause) {
        super(cause);
    }

    protected UserAuthenticationErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
