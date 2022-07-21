package zw.co.codehive.transactiondesignpatterns.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.codehive.commons.util.OtherUtils;
import zw.co.codehive.transactiondesignpatterns.exception.BusinessValidationException;
import zw.co.codehive.transactiondesignpatterns.service.*;
import zw.co.codehive.transactiondesignpatterns.dto.transaction.purchase.PurchaseRequestDto;
import zw.co.codehive.transactiondesignpatterns.enums.TransactionStatus;
import zw.co.codehive.transactiondesignpatterns.enums.TransactionType;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.PurchaseTransaction;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Transaction;
import zw.co.codehive.transactiondesignpatterns.repository.fudzo.PurchaseTransactionRepository;
import zw.co.jugaad.transactiondesignpatterns.service.*;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PurchaseTransactionServiceImpl implements PurchaseTransactionService {

    private final PurchaseTransactionRepository transactionRepository;
    private final AttendantService attendantService;
    private final ProductService productService;
    private final TerminalService terminalService;
    private final CardService cardService;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void transact(PurchaseRequestDto purchaseRequestDto) {
        var transaction = new PurchaseTransaction();

        var product = productService.findByProductName(purchaseRequestDto.getProductName());

        var attendant = attendantService.findByAttendantId(purchaseRequestDto.getAttendantId());

        var terminal = terminalService.findByTerminalId(purchaseRequestDto.getTerminalId());

        //account card
        var card = cardService.findByPan(purchaseRequestDto.getCardPan());


        //get debit card when making a purchase
        var debitAccount = accountService.getAccount(purchaseRequestDto.getFromAccount());


        transaction.setFromAccount(debitAccount);
        //set attendant
        transaction.setAttendant(attendant);

        //set terminal
        transaction.setTerminal(terminal);

        //set product name as product
        transaction.setProduct(product);

        //get Amount
        var amount = purchaseRequestDto.getAmount();

        //set Amount
        transaction.setAmount(amount);

        //check if card is funded sufficiently
        if (card.balance.compareTo(amount) < 0) throw new BusinessValidationException("insufficient.funds");

        //card must come from the debit account
        if (!debitAccount.getCards().get(0).getPan().equals(card.getPan()))
            throw new BusinessValidationException("card.does.not.belong.to.account");

        transaction.setCard(card);

        var debitBalance = card.balance.subtract(transaction.getAmount());

        //set new balance on transaction
        debitAccount.setBalance(debitBalance);

        //get credit account
        var creditAccount = accountService.getAccount(purchaseRequestDto.getToAccount());

        //set toAccount
        transaction.setToAccount(creditAccount);

        var creditBalance = creditAccount.balance.add(transaction.getAmount());

        creditAccount.setBalance(creditBalance);

        //set account transaction type to purchase
        transaction.setTransactionType(TransactionType.PURCHASE);

        transaction.setEntryType(purchaseRequestDto.getEntryType());

        //calculate litres used from the amount and unit price from product
        var litres = getLitres(transaction.getAmount(), product.getPrice());

        //set litres used from the amount and unit price from product
        transaction.setLitres(litres);

        var quantity = deduceQuantity(transaction.getLitres(), product.getQuantity());
        //deduct litres from quantity
        product.setQuantity(quantity);

        if (quantity.equals(BigDecimal.ZERO)) throw new BusinessValidationException("product.unavailable");
        //set TransactionReference
        transaction.setTransactionReference(OtherUtils.generateReferenceNumber());

        //set card number here
        transaction.setCard(card);

        var pin = card.getPin();

        //get transaction pin from debit account card
        if (!passwordEncoder.matches(purchaseRequestDto.getPin(), pin))
            throw new BusinessValidationException("incorrect.pin.entered");

        //set pin
        transaction.setPin(passwordEncoder.encode(pin));

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
    public List<PurchaseTransaction> getAllPendingValidation() {

        var deposits = transactionRepository.findAllByIsApproved(false);

        if (deposits.isEmpty()) throw new BusinessValidationException("no.transactions.available.at.the.moment");

        deposits.removeIf(Transaction::getIsCancelled);

        return deposits;
    }

    public BigDecimal getLitres(BigDecimal amount, BigDecimal price) {

        return amount.divide(price, RoundingMode.HALF_EVEN).setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal deduceQuantity(BigDecimal litres, BigDecimal quantity) {

        return quantity.subtract(litres);
    }
}