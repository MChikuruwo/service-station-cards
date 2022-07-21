package zw.co.codehive.transactiondesignpatterns.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Car;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Card;
import zw.co.codehive.transactiondesignpatterns.dto.card.AddCardRequestDto;
import zw.co.codehive.transactiondesignpatterns.dto.card.ChangePinRequestDto;

import java.util.List;

public interface CardService {

    Card save(AddCardRequestDto requestDto) ;

    void changePin(ChangePinRequestDto requestDto);

    Page<Card> getAllCards(Pageable pageable);

    Card getCard(Long id);

    void deleteCard(Long id);

    Card getOne(Long id);

    void blockCard(String cardNumber);

    void cancelCard(String cardNumber);

    void unblockCard(String cardNumber);

    Card findByPan(String pan);

    List<Card> findAllByOrganisation(String organisation);


    Page<Card> findAllByCarsIn(List<Card> cards, Pageable pageable);

    Page<Card> findAllByIsActive(Boolean active, Pageable pageable);

    Page<Card> findAllByIsBlocked(Boolean blocked,Pageable pageable);

    Page<Card> findAllByIsCancelled(Boolean cancelled,Pageable pageable);

    List<Card> findAllByPendingStatusAndIsActiveFalse(String status);

    Card addCars(Card card, Car car);

    void removeCar(Card card,Car car);

    Card findByAccount_AccountNumber(String accountNumber);

    void saveAccount(String cardPan,String accountNumber);
}