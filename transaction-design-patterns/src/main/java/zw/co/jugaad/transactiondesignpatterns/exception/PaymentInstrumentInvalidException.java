package zw.co.jugaad.transactiondesignpatterns.exception;

public class PaymentInstrumentInvalidException extends RuntimeException{
    public PaymentInstrumentInvalidException(String message) {
        super(message);
    }
}
