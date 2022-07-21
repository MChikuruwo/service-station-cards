package zw.co.jugaad.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.CardTransferTransaction;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface CardTransferTransactionRepository extends JpaRepository<CardTransferTransaction, Long> {

    Optional<CardTransferTransaction> findByTransactionReference(String transactionReference);

    List<CardTransferTransaction> findAllByIsApproved(boolean approval);
}