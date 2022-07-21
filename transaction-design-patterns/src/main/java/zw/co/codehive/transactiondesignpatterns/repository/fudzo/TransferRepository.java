package zw.co.codehive.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.CardTransferTransaction;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface TransferRepository extends JpaRepository<CardTransferTransaction,Long> {
}
