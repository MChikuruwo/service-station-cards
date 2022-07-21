package zw.co.codehive.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Account;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.CompanyAccount;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.IndividualAccount;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface AccountRepository extends JpaRepository<Account, String> {


    Page<Account> findAllByOwner_Id(Long id, Pageable pageable);

    Page<CompanyAccount> findAllCompanyByOwner_Id(Long id, Pageable pageable);

    Page<IndividualAccount> findAllIndividualByOwner_Id(Long id, Pageable pageable);

    Account findByAccountNumber (@Param("accountNumber") String policyNumber);
}