package zw.co.jugaad.transactiondesignpatterns.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }
}
