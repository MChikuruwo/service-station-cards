package zw.co.codehive.transactiondesignpatterns.factory;

import zw.co.codehive.transactiondesignpatterns.model.fudzo.Account;
import zw.co.codehive.transactiondesignpatterns.dto.account.AccountRequestDto;

public interface FuelAccount {

Account createAccount(AccountRequestDto individualAccountRequestDto);
}
