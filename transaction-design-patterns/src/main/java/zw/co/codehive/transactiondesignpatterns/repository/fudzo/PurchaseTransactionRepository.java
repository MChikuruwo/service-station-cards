package zw.co.codehive.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zw.co.codehive.transactiondesignpatterns.enums.TransactionType;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.PurchaseTransaction;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseTransactionRepository extends JpaRepository<PurchaseTransaction, Long> {


    Page<PurchaseTransaction> findAllByTransactionType(TransactionType transactionType, Pageable pageable);

    Optional<PurchaseTransaction> findByTransactionReference(String transactionReference);

    List<PurchaseTransaction> findAllByIsApproved(boolean approval);
}
