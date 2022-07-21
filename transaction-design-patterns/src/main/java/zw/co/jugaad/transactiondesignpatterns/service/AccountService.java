package zw.co.jugaad.transactiondesignpatterns.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.jugaad.fudzocommons.exceptions.BusinessValidationException;
import zw.co.jugaad.transactiondesignpatterns.dto.UpdateAccountRequest;
import zw.co.jugaad.transactiondesignpatterns.dto.account.AccountRequestDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Account;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Card;

import java.util.List;

public interface AccountService {

    Account saveCompanyAccount(AccountRequestDto requestDto) throws BusinessValidationException;

    Account saveIndividualAccount(AccountRequestDto requestDto) throws BusinessValidationException;

    void updateAccount(AccountRequestDto requestDto);

     void addCard(Account account,Card card);

    Page<Account> getAllAccounts(Pageable pageable);

    Account getAccount(String accountNumber);

    void deleteAccount(String accountNumber);

    Account getOne(String accountNumber);

    Account findAccountByCardPan(String cardPan);
}