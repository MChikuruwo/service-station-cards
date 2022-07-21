package zw.co.jugaad.transactiondesignpatterns.service;

import zw.co.jugaad.transactiondesignpatterns.dto.transaction.purchase.PurchaseRequestDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.PurchaseTransaction;

import java.util.List;

public interface PurchaseTransactionService {

    void transact(PurchaseRequestDto purchaseRequestDto);

    void reverse(String transactionReference);

    void validate(String transactionReference);

    void cancel(String transactionReference);

    List<PurchaseTransaction> getAllPendingValidation();
}