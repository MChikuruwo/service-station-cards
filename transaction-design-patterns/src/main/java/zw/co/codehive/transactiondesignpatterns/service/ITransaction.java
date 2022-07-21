package zw.co.codehive.transactiondesignpatterns.service;

import zw.co.codehive.transactiondesignpatterns.exception.PaymentFailedException;
import zw.co.codehive.transactiondesignpatterns.exception.PaymentInstrumentInvalidException;
import zw.co.codehive.transactiondesignpatterns.dto.transaction.TransactionResponse;

public interface ITransaction {

    void validate() throws PaymentInstrumentInvalidException;
    TransactionResponse collectPayment() throws PaymentFailedException;
}
