package zw.co.codehive.transactiondesignpatterns.service;

import zw.co.codehive.transactiondesignpatterns.dto.transaction.transfer.CardTransferTransactionRequestDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.CardTransferTransaction;

import java.util.List;

public interface CardTransferTransactionService {

    void transact(CardTransferTransactionRequestDto transactionRequestDto);

    void reverse(String transactionReference);

    void validate(String transactionReference);

    void cancel(String transactionReference);

    List<CardTransferTransaction> getAllPendingValidation();
}
