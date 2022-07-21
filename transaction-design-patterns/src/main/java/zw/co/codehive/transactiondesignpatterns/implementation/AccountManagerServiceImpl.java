package zw.co.codehive.transactiondesignpatterns.implementation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.codehive.transactiondesignpatterns.dto.account.AccountManagerRequestDto;
import zw.co.codehive.transactiondesignpatterns.exception.BusinessValidationException;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Account;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.AccountManager;
import zw.co.codehive.transactiondesignpatterns.repository.fudzo.AccountManagerRepository;
import zw.co.codehive.transactiondesignpatterns.service.AccountManagerService;
import zw.co.codehive.transactiondesignpatterns.service.AccountService;
import zw.co.codehive.transactiondesignpatterns.service.OwnerService;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountManagerServiceImpl implements AccountManagerService {

    private final AccountManagerRepository accountManagerRepository;
    private final AccountService accountService;
    private final OwnerService ownerService;

    @Override
    public AccountManager save(AccountManagerRequestDto requestDto) {

        Account companyAccount = accountService.getOne(requestDto.getCompanyAccount());

        var owner = ownerService.findOwnerByCompanyName(requestDto.getCompanyName());

        AccountManager accountManager = AccountManager.builder()
                .companyAccount(Collections.singletonList(companyAccount))
                .email(owner.getEmail())
                .surname(owner.getLastName())
                .firstname(owner.getFirstName())
                .mobile(owner.getMobile())
                .owner(owner)
                .build();

        return accountManagerRepository.save(accountManager);
    }

    @Override
    public AccountManager update(AccountManagerRequestDto requestDto) {
        Account companyAccount = accountService.getOne(requestDto.getCompanyAccount());
        var owner = ownerService.findOwnerByCompanyName(requestDto.getCompanyName());
        AccountManager accountManager = AccountManager.builder()
                .companyAccount(Collections.singletonList(companyAccount))
                .owner(owner)
                .build();

       return accountManagerRepository.save(accountManager);
    }

    @Override
    public Page<AccountManager> getAllAccountManagers(Pageable pageable) {
        Page<AccountManager> accountManagers = accountManagerRepository.findAll(pageable);

        if (accountManagers.isEmpty())
            throw new BusinessValidationException("No account managers available at the moment");

        return accountManagers;
    }

    @Override
    public AccountManager getAccountManager(Long id) {
        return getOne(id);
    }

    @Override
    public void deleteAccountManager(Long id) {
        Optional<AccountManager> accountManager = accountManagerRepository.deleteAccountManagerById(id);
        if (!accountManager.isPresent())
            throw new BusinessValidationException("Account manager with id " + id + " does not exist");

    }

    @Override
    public AccountManager getOne(Long id) {
        return accountManagerRepository.findById(id).orElseThrow(
                () -> new BusinessValidationException("Account manager with id " + id + " does not exist")
        );
    }
}