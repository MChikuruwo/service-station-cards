package zw.co.codehive.transactiondesignpatterns.service;

import zw.co.codehive.transactiondesignpatterns.model.fudzo.Account;

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