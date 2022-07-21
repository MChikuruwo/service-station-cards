package zw.co.codehive.transactiondesignpatterns.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zw.co.codehive.commons.enums.Organisation;
import zw.co.codehive.transactiondesignpatterns.dto.card.AddCardRequestDto;
import zw.co.codehive.transactiondesignpatterns.dto.card.ChangePinRequestDto;
import zw.co.codehive.transactiondesignpatterns.exception.BusinessValidationException;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Car;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Card;
import zw.co.codehive.transactiondesignpatterns.repository.fudzo.CardRepository;
import zw.co.codehive.transactiondesignpatterns.service.CardService;
import zw.co.codehive.transactiondesignpatterns.enums.Status;
import zw.co.codehive.transactiondesignpatterns.service.AccountService;
import zw.co.codehive.transactiondesignpatterns.service.CarService;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;

    private final PasswordEncoder passwordEncoder;

    @Resource
    private AccountService accountService;

    @Resource
    private CarService carService;

    @Override
    public Card save(AddCardRequestDto requestDto) {

        var pin = requestDto.getPin();

        if (!NumberUtils.isDigits(pin))
            throw new BusinessValidationException("user.pin.must.contain.numbers.only");

        if (pin.length() != 4)
            throw new BusinessValidationException("pin.length.must.be.4");

        var card = Card.builder()
                .pan(requestDto.getPan())
                .organisation(Organisation.valueOf(requestDto.getOrganisation().name()))
                .pin(passwordEncoder.encode(pin))
                .balance(requestDto.getBalance())
                .status(String.valueOf(Status.Pending))
                .isActive(false)
                .isCancelled(false)
                .isBlocked(false)
                .build();
        return cardRepository.save(card);
    }

    @Override
    public void changePin(ChangePinRequestDto requestDto) {
        var card = findByPan(requestDto.getPan());

        var pin = requestDto.getPin();

        if (!NumberUtils.isDigits(pin))
            throw new BusinessValidationException("user.pin.must.contain.numbers.only");

        if (pin.length() != 4)
            throw new BusinessValidationException("pin.length.must.be.4");

        card.setPin(passwordEncoder.encode(pin));

        cardRepository.save(card);
    }

    @Override
    public Page<Card> getAllCards(Pageable pageable) {
        return cardRepository.findAll(pageable);
    }

    @Override
    public Card getCard(Long id) {
        return cardRepository.findById(id).orElseThrow(() ->
                new BusinessValidationException("card number " + id + " not found"));
    }

    @Override
    public void deleteCard(Long id) {
        var card = getOne(id);

        if (card == null) throw new BusinessValidationException("card number " + id + " not found");

        cardRepository.delete(card);
    }

    @Override
    public Card getOne(Long id) {

        var card = cardRepository.findById(id);

        if (card.isEmpty()) throw new BusinessValidationException("card number " + id + " not found");

        return card.get();
    }

    @Override
    public void blockCard(String cardNumber) {
        var card = findByPan(cardNumber);

        card.setIsBlocked(true);

        card.setIsActive(false);

        card.setBlockedOn(LocalDateTime.now());

        cardRepository.save(card);
    }


    @Override
    public void cancelCard(String cardNumber) {
        var card = findByPan(cardNumber);

        card.setIsCancelled(true);

        card.setIsActive(false);

        card.setCancelledOn(LocalDateTime.now());

        cardRepository.save(card);
    }

    @Override
    public void unblockCard(String cardNumber) {

        var card = findByPan(cardNumber);

        card.setIsBlocked(false);

        card.setIsActive(true);

        card.setActivatedOn(LocalDateTime.now());

        cardRepository.save(card);
    }

    @Override
    public Card findByPan(String pan) {
        var card = cardRepository.findByPan(pan);

        if (card.isEmpty()) throw new BusinessValidationException("card with pan: " + pan + " not found");

        return card.get();
    }

    @Override
    public List<Card> findAllByOrganisation(String organisation) {

        var cards = cardRepository.findAllByOrganisation(organisation);

        if (cards.isEmpty()) throw new BusinessValidationException("cards.under: " + organisation + " not.found");

        return cards;
    }

    @Override
    public Page<Card> findAllByCarsIn(List<Card> cards, Pageable pageable) {
        cards = cardRepository.findAll();
        return cardRepository.findAllByCarsIn(cards, pageable);
    }

    @Override
    public Page<Card> findAllByIsActive(Boolean active, Pageable pageable) {
        return cardRepository.findAllByIsActive(true, pageable);
    }

    @Override
    public Page<Card> findAllByIsBlocked(Boolean blocked, Pageable pageable) {
        var cards = cardRepository.findAllByIsBlocked(true, pageable);
        return cards;
    }

    @Override
    public Page<Card> findAllByIsCancelled(Boolean cancelled, Pageable pageable) {
        return cardRepository.findAllByIsCancelled(cancelled, pageable);
    }

    @Override
    public List<Card> findAllByPendingStatusAndIsActiveFalse(String status) {
        var cards = cardRepository.findAllByStatusAndIsActiveFalse(Status.Pending.name());

        if (cards==null) throw new BusinessValidationException("cards.not.found");

        return cards;
    }

    @Override
    public Card addCars(Card card, Car car) {

        var requestCard = findByPan(card.getPan());

        var cars = requestCard.getCars();

        cars.add(car);

        requestCard.setCars(cars);

        return requestCard;
    }

    @Override
    public void removeCar(Card card, Car car) {
        var requestCard = findByPan(card.getPan());

        var requestedCar = carService.getCar(car.getRegistrationNumber());

        var cars = requestCard.getCars();

        cars.remove(requestedCar);

        cardRepository.save(requestCard);
    }

    @Override
    public Card findByAccount_AccountNumber(String accountNumber) {
        var card = cardRepository.findByAccount_AccountNumber(accountNumber);

        if (card == null) throw new BusinessValidationException("no.card.for.account.number: " + accountNumber);

        return card;
    }

    @Override
    public void saveAccount(String cardPan, String accountNumber) {
        var card = findByPan(cardPan);

        var account = accountService.getAccount(accountNumber);

        if (account == null) throw new BusinessValidationException("account.number: " + accountNumber + " not.found");

        card.setAccount(account);

        card.setIsActive(true);

        card.setStatus(String.valueOf(Status.Active));

        cardRepository.save(card);
    }
}