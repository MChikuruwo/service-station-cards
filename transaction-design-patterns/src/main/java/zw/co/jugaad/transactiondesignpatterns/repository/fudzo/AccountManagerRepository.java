package zw.co.jugaad.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.AccountManager;
import zw.co.jugaad.transactiondesignpatterns.projection.AccountManagerResultDto;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface AccountManagerRepository extends JpaRepository<AccountManager, Long> {

List<AccountManager> findAllByCompanyAccount(String accountNumber);

Optional<AccountManager> deleteAccountManagerById(Long id);

@Query(value = "select account_manager.id as id,\n" +
        "       account_manager.email as email,\n" +
        "       account_manager.firstname as firstName,\n" +
        "       account_manager.surname as surname,\n" +
        "       account_manager.mobile as mobile,\n" +
        "       company_name as companyName\n" +
        "from account_manager\n" +
        "left join account a on a.account_number = account_manager.company_account_account_number\n" +
        "where account_manager.id = ?1", nativeQuery = true)
Optional<AccountManagerResultDto> findAccountManagerById(Long id);
}