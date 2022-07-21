package zw.co.jugaad.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.jugaad.transactiondesignpatterns.enums.TransactionType;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Transaction;

import java.util.Optional;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAll(Pageable pageable);

    Page<Transaction> findAllByTransactionType(TransactionType transactionType,Pageable pageable);

    Optional<Transaction> findByTransactionReference(String transactionReference);

}