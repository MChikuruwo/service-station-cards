package zw.co.codehive.transactiondesignpatterns.service;

import zw.co.codehive.transactiondesignpatterns.dto.transaction.TransactionRequestDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.DepositTransaction;

import java.util.List;

public interface DepositTransactionService {

    void transact(TransactionRequestDto depositRequestDto);

    void reverse(String transactionReference);

    void validate(String transactionReference);

    void cancel(String transactionReference);

    List<DepositTransaction> getAllPendingValidation();
}