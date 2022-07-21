package zw.co.jugaad.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Card;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface CardTopUpRepository extends JpaRepository<Card, Long> {

    Card findByPan(@Param("pan") String pan);

}