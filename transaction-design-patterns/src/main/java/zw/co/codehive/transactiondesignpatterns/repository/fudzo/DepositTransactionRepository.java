package zw.co.codehive.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.codehive.transactiondesignpatterns.enums.TransactionType;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.DepositTransaction;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface DepositTransactionRepository extends JpaRepository<DepositTransaction, Long> {

    Page<DepositTransaction> findAllByTransactionType(TransactionType transactionType, Pageable pageable);

    Optional<DepositTransaction> findByTransactionReference(String transactionReference);

    List<DepositTransaction> findAllByIsApproved(boolean approval);

}
