package zw.co.jugaad.transactiondesignpatterns.factory;

import zw.co.jugaad.transactiondesignpatterns.dto.account.AccountRequestDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Account;

public interface FuelAccount {

Account createAccount(AccountRequestDto individualAccountRequestDto);
}
