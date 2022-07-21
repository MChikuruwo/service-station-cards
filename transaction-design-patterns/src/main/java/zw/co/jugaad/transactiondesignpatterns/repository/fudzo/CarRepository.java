package zw.co.jugaad.transactiondesignpatterns.repository.fudzo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Car;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Card;

import java.util.List;

@Repository
@Transactional(transactionManager = "mysqlTransactionManager")
public interface CarRepository extends JpaRepository<Car, String> {

    Page<Car> findAllByCard_Pan(String cardNumber, Pageable pageable);
    List<Car> findCarsByCard(Card card);
    Car deleteCarByRegistrationNumber(String registrationNumber);
    Car findByRegistrationNumber(String registrationNumber);
}
