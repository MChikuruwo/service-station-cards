package zw.co.jugaad.transactiondesignpatterns.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zw.co.jugaad.fudzocommons.util.OtherUtils;
import zw.co.jugaad.transactiondesignpatterns.dto.transaction.TransactionRequestDto;
import zw.co.jugaad.transactiondesignpatterns.enums.TransactionStatus;
import zw.co.jugaad.transactiondesignpatterns.enums.TransactionType;
import zw.co.jugaad.transactiondesignpatterns.exception.BusinessValidationException;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.DepositTransaction;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Transaction;
import zw.co.jugaad.transactiondesignpatterns.repository.fudzo.DepositTransactionRepository;
import zw.co.jugaad.transactiondesignpatterns.service.AccountService;
import zw.co.jugaad.transactiondesignpatterns.service.DepositTransactionService;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class DepositTransactionImpl implements DepositTransactionService {


    private final AccountService accountService;

    private final DepositTransactionRepository transactionRepository;


    @Override
    public void transact(TransactionRequestDto depositRequestDto) {

        var transaction = new DepositTransaction();

        //get debit  account
        var debitAccount = accountService.getAccount(depositRequestDto.getFromAccount());

        transaction.setFromAccount(debitAccount);

        //get Amount
        var amount = depositRequestDto.getAmount();

        //set Amount to transaction
        transaction.setAmount(amount);

        if (debitAccount.balance.compareTo(amount) < 0) throw new BusinessValidationException("insufficient.funds");

        //get accounts here and perform deposit, i.e debit ,the giver and credit the receiver
        var debitBalance = debitAccount.balance.subtract(transaction.getAmount());

        //set new balance on transaction
        debitAccount.setBalance(debitBalance);

        //get credit account
        var creditAccount = accountService.getAccount(depositRequestDto.getToAccount());

        transaction.setToAccount(creditAccount);

        var creditBalance = creditAccount.balance.add(transaction.getAmount());

        creditAccount.setBalance(creditBalance);

        //set account transaction type to deposit
        transaction.setTransactionType(TransactionType.DEPOSIT);

        transaction.setEntryType(depositRequestDto.getEntryType());

        //set TransactionReference
        transaction.setTransactionReference(OtherUtils.generateReferenceNumber());
        //save transaction
        transactionRepository.save(transaction);
    }

    @Override
    public void reverse(String transactionReference) {
        var transactionFromDB = transactionRepository.findByTransactionReference(transactionReference);

        var debitAccount = accountService.getAccount(transactionFromDB.get().getFromAccount().accountNumber);

        var reversalAmount = transactionFromDB.get().getAmount();

        var debitReversalBalance = debitAccount.balance.add(reversalAmount);

        debitAccount.setBalance(debitReversalBalance);

        var creditAccount = accountService.getAccount(transactionFromDB.get().getToAccount().accountNumber);

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

        var deposit = transactionFromDB.get();

        if (deposit.getIsCancelled())
            throw new BusinessValidationException("cancelled.transaction.cannot.be.validated.");

        deposit.setIsApproved(true);

        transactionFromDB.get().setTransactionStatus(TransactionStatus.APPROVED);

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
    public List<DepositTransaction> getAllPendingValidation() {

        var deposits = transactionRepository.findAllByIsApproved(false);

        if (deposits.isEmpty()) throw new BusinessValidationException("no.transactions.available.at.the.moment");

        deposits.removeIf(Transaction::getIsCancelled);

        return deposits;
    }
}