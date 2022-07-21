package zw.co.codehive.transactiondesignpatterns.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.codehive.commons.util.AccountNumberGenerator;
import zw.co.codehive.commons.util.OtherUtils;
import zw.co.codehive.transactiondesignpatterns.dto.account.AccountRequestDto;
import zw.co.codehive.transactiondesignpatterns.dto.account.IndividualAccountRequestDto;
import zw.co.codehive.transactiondesignpatterns.exception.BusinessValidationException;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Account;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Card;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.CompanyAccount;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.IndividualAccount;
import zw.co.codehive.transactiondesignpatterns.repository.fudzo.AccountRepository;
import zw.co.codehive.transactiondesignpatterns.service.CardService;
import zw.co.codehive.transactiondesignpatterns.service.OwnerService;
import zw.co.codehive.transactiondesignpatterns.enums.UserType;
import zw.co.codehive.transactiondesignpatterns.service.AccountService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final OwnerService ownerService;

    @Resource
    private CardService cardService;

    @Override
    public Account saveCompanyAccount(AccountRequestDto requestDto) throws zw.co.codehive.commons.exceptions.BusinessValidationException {

        var owner = ownerService.findOwnerByCompanyName(requestDto.getCompanyName());

        CompanyAccount account = new CompanyAccount();
        account.setAccountNumber(AccountNumberGenerator.generate(UserType.COMPANY.name()));
        account.setBalance(BigDecimal.ZERO);
        account.setCompanyName(owner.getCompanyName());
        account.setRegistrationNumber(OtherUtils.generateRegistrationNumber());
        account.setContactNumber(owner.getMobile());
        account.setEmail(owner.getEmail());
        account.setOwner(owner);

        return accountRepository.save(account);
    }

    @Override
    public Account saveIndividualAccount(AccountRequestDto requestDto) throws zw.co.codehive.commons.exceptions.BusinessValidationException {

        var owner = ownerService.findOwnerByCompanyName(requestDto.getCompanyName());

        IndividualAccountRequestDto individualAccountRequestDto = (IndividualAccountRequestDto) requestDto;
        var  account = new IndividualAccount();
        account.setAccountNumber(AccountNumberGenerator.generate(UserType.INDIVIDUAL.name()));
        account.setEmail(owner.getEmail());
        account.setMobile(owner.getMobile());
        account.setFirstname(owner.getFirstName());
        account.setLastname(owner.getLastName());
        account.setIdNumber(individualAccountRequestDto.getIdNumber());
        account.setBalance(BigDecimal.ZERO);
        account.setOwner(owner);
        return accountRepository.save(account);
    }


    @Override
    public void updateAccount(AccountRequestDto requestDto) {
        //TODO add account update dtos for company and individuals

    }

    @Override
    public void addCard(Account account, Card card) {
        var requestAccount = getOne(account.getAccountNumber());

        var cards = requestAccount.getCards();

        cards.add(card);

        requestAccount.setCards(cards);

        card.setIsActive(true);

        card.setActivatedOn(LocalDateTime.now());

        cardService.saveAccount(card.getPan(), account.getAccountNumber());

        accountRepository.save(requestAccount);
    }

    @Override
    public Page<Account> getAllAccounts(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Account getAccount(String accountNumber) {
        return getOne(accountNumber);
    }

    @Override
    public void deleteAccount(String accountNumber) {
        var account = accountRepository.findByAccountNumber(accountNumber);

        if (account == null)
            throw new BusinessValidationException("Account with account number " + accountNumber + " does not exist");

        accountRepository.delete(account);
    }

    @Override
    public Account getOne(String accountNumber) {
        return accountRepository.findById(accountNumber).orElseThrow(
                () -> new BusinessValidationException("Account number " + accountNumber + " does not exist")
        );
    }

    @Override
    public Account findAccountByCardPan(String cardPan) {
        var card = cardService.findByPan(cardPan);

        var account = card.getAccount();

        return account;
    }

}