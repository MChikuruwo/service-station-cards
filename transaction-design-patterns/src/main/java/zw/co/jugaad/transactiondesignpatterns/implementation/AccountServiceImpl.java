package zw.co.jugaad.transactiondesignpatterns.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.jugaad.fudzocommons.util.AccountNumberGenerator;
import zw.co.jugaad.fudzocommons.util.OtherUtils;
import zw.co.jugaad.transactiondesignpatterns.dto.UpdateAccountRequest;
import zw.co.jugaad.transactiondesignpatterns.dto.account.AccountRequestDto;
import zw.co.jugaad.transactiondesignpatterns.dto.account.IndividualAccountRequestDto;
import zw.co.jugaad.transactiondesignpatterns.enums.UserType;
import zw.co.jugaad.transactiondesignpatterns.exception.BusinessValidationException;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Account;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Card;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.CompanyAccount;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.IndividualAccount;
import zw.co.jugaad.transactiondesignpatterns.repository.fudzo.AccountRepository;
import zw.co.jugaad.transactiondesignpatterns.service.AccountService;
import zw.co.jugaad.transactiondesignpatterns.service.CardService;
import zw.co.jugaad.transactiondesignpatterns.service.OwnerService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final OwnerService ownerService;

    @Resource
    private  CardService cardService;

    @Override
    public Account saveCompanyAccount(AccountRequestDto requestDto) throws zw.co.jugaad.fudzocommons.exceptions.BusinessValidationException {

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
    public Account saveIndividualAccount(AccountRequestDto requestDto) throws zw.co.jugaad.fudzocommons.exceptions.BusinessValidationException {

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
    public void addCard(Account account,Card card) {
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