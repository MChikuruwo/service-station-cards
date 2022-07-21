package zw.co.jugaad.transactiondesignpatterns.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zw.co.jugaad.fudzocommons.util.OtherUtils;
import zw.co.jugaad.transactiondesignpatterns.dto.transaction.transfer.CardTransferTransactionRequestDto;
import zw.co.jugaad.transactiondesignpatterns.enums.TransactionStatus;
import zw.co.jugaad.transactiondesignpatterns.enums.TransactionType;
import zw.co.jugaad.transactiondesignpatterns.exception.BusinessValidationException;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.CardTransferTransaction;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Transaction;
import zw.co.jugaad.transactiondesignpatterns.repository.fudzo.CardTransferTransactionRepository;
import zw.co.jugaad.transactiondesignpatterns.service.AccountService;
import zw.co.jugaad.transactiondesignpatterns.service.CardService;
import zw.co.jugaad.transactiondesignpatterns.service.CardTransferTransactionService;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CardTransferTransactionServiceImpl implements CardTransferTransactionService {

    private final CardTransferTransactionRepository transactionRepository;

    private final CardService cardService;

    private final AccountService accountService;


    @Override
    public void transact(CardTransferTransactionRequestDto transactionRequestDto) {

        var transaction = new CardTransferTransaction();

        //get debit  account
        var debitAccount = accountService.getAccount(transactionRequestDto.getFromAccount());

        transaction.setFromAccount(debitAccount);

        var amount = transactionRequestDto.getAmount();

        //get Amount
        transaction.setAmount(amount);

        if (debitAccount.balance.compareTo(amount) < 0) throw new BusinessValidationException("insufficient.funds");

            //get accounts here and perform deposit, i.e debit ,the giver and credit the receiver
        var debitBalance = debitAccount.balance.subtract(transaction.getAmount());


        //set new balance on transaction
        debitAccount.setBalance(debitBalance);

        //get credit account
        var creditAccount = cardService.findByPan(transactionRequestDto.getCardPan());

        transaction.setCard(Collections.singletonList(creditAccount));

        //check if card is not blocked or cancelled
        if (creditAccount.getIsBlocked() || creditAccount.getIsCancelled())
            throw new BusinessValidationException("transfer.failed.card.is.either.blocked.or.cancelled");
        //add balance to card
        var creditBalance = creditAccount.balance.add(transaction.getAmount());

        //set balance to card
        creditAccount.setBalance(creditBalance);

        //set  transaction type to transfer
        transaction.setTransactionType(TransactionType.TRANSFER);

        transaction.setEntryType(transactionRequestDto.getEntryType());

        //set TransactionReference
        transaction.setTransactionReference(OtherUtils.generateReferenceNumber());
        //save transaction
        transactionRepository.save(transaction);
    }

    @Override
    public void reverse(String transactionReference) {

        var transactionFromDB = transactionRepository.findByTransactionReference(transactionReference);

        if (transactionFromDB.get().getIsApproved())
            throw new BusinessValidationException("approved.transaction.cannot.be.reversed.");

        var debitAccount = accountService.getAccount(transactionFromDB.get().getFromAccount().accountNumber);

        var reversalAmount = transactionFromDB.get().getAmount();

        var debitReversalBalance = debitAccount.balance.add(reversalAmount);

        debitAccount.setBalance(debitReversalBalance);

        var creditAccount = cardService.findByPan(transactionFromDB.get().getCard().get(0).getPan());

        var creditReversalBalance = creditAccount.balance.subtract(reversalAmount);

        creditAccount.setBalance(creditReversalBalance);

        //logic on reversal from both accounts
        transactionFromDB.get().setReversed(true);

        transactionFromDB.get().setTransactionStatus(TransactionStatus.REVERSED);

        transactionRepository.save(transactionFromDB.get());
    }

    @Override
    public void validate(String transactionReference) {

        var transactionFromDB = transactionRepository.findByTransactionReference(transactionReference);

        var transfer = transactionFromDB.get();

        if (transfer.getIsCancelled())
            throw new BusinessValidationException("cancelled.transaction.cannot.be.validated.");

        transfer.setIsApproved(true);

        transfer.setTransactionStatus(TransactionStatus.APPROVED);

        transactionRepository.save(transactionFromDB.get());

    }

    @Override
    public void cancel(String transactionReference) {

        var transactionFromDB = transactionRepository.findByTransactionReference(transactionReference)

                .orElseThrow(() -> new BusinessValidationException("transaction.with.reference " + transactionReference + " not.found"));

        if (transactionFromDB.getIsApproved())
            throw new BusinessValidationException("approved.transaction.cannot.be.cancelled.");

        transactionFromDB.setIsCancelled(true);

        transactionFromDB.setTransactionStatus(TransactionStatus.CANCELLED);

        transactionRepository.save(transactionFromDB);
    }

    @Override
    public List<CardTransferTransaction> getAllPendingValidation() {

        var transfers = transactionRepository.findAllByIsApproved(false);

        if (transfers.isEmpty()) throw new BusinessValidationException("no.transactions.available.at.the.moment");

        transfers.removeIf(Transaction::getIsCancelled);

        return transfers;
    }
}
