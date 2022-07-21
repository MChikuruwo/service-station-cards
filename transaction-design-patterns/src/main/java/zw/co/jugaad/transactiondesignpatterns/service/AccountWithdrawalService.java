package zw.co.jugaad.transactiondesignpatterns.service;

import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Account;

import java.math.BigDecimal;

public class AccountWithdrawalService {

    private Account account;

    public AccountWithdrawalService(Account account) {
        this.account = account;
    }

    public void withdraw(BigDecimal amount) {
        account.withdraw(amount);
    }
}