package zw.co.jugaad.transactiondesignpatterns.service;

import zw.co.jugaad.transactiondesignpatterns.dto.transaction.TransactionResponse;
import zw.co.jugaad.transactiondesignpatterns.exception.PaymentFailedException;
import zw.co.jugaad.transactiondesignpatterns.exception.PaymentInstrumentInvalidException;

public interface ITransaction {

    void validate() throws PaymentInstrumentInvalidException;
    TransactionResponse collectPayment() throws PaymentFailedException;
}
