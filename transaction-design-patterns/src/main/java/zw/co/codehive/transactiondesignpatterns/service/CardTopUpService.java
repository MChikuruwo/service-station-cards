package zw.co.codehive.transactiondesignpatterns.service;

import java.math.BigDecimal;

public interface CardTopUpService {

    void deposit(BigDecimal amount);

    void withdraw(BigDecimal amount);
}
