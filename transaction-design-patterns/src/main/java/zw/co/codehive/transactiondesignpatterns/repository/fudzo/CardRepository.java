package zw.co.codehive.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Card;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface CardRepository extends JpaRepository<Card, Long> {

    Optional<Card> findByPan(@Param("pan") String pan);

    Page<Card> findAllByPan(String pan, Pageable pageable);

    Page<Card> findAllByCarsIn(List<Card> cards,Pageable pageable);

    Page<Card> findAllByIsActive(Boolean active, Pageable pageable);

    Page<Card> findAllByIsBlocked(Boolean blocked,Pageable pageable);

    Page<Card> findAllByIsCancelled(Boolean cancelled,Pageable pageable);

    List<Card> findAllByOrganisation(@Param("organisation") String organisation);

    Card findByAccount_AccountNumber(@Param("accountNumber")String accountNumber);

    List<Card> findAllByStatusAndIsActiveFalse(String status);
}