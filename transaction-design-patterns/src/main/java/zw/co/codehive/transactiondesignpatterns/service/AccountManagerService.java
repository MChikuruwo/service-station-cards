package zw.co.codehive.transactiondesignpatterns.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.codehive.transactiondesignpatterns.dto.account.AccountManagerRequestDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.AccountManager;

public interface AccountManagerService {

    AccountManager save(AccountManagerRequestDto requestDto) ;

    AccountManager update(AccountManagerRequestDto requestDto);


    Page<AccountManager> getAllAccountManagers(Pageable pageable);

    AccountManager getAccountManager(Long id);

    void deleteAccountManager(Long id);

    AccountManager getOne(Long id);
}
