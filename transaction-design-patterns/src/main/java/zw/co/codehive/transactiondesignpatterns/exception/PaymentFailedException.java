package zw.co.codehive.transactiondesignpatterns.exception;

public class PaymentFailedException extends Exception {
    public PaymentFailedException(String message) {
        super(message);
    }
}
