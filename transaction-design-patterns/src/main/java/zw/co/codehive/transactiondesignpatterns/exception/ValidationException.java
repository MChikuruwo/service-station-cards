package zw.co.codehive.transactiondesignpatterns.exception;

public class ValidationException extends RuntimeException{
    public ValidationException(String message) {
        super(message);
    }
}
