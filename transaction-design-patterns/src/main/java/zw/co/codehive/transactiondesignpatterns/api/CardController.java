package zw.co.codehive.transactiondesignpatterns.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.codehive.commons.api.ApiResponse;
import zw.co.codehive.transactiondesignpatterns.dto.response.CardResponse;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Card;
import zw.co.codehive.transactiondesignpatterns.response.GenericResponse;
import zw.co.codehive.transactiondesignpatterns.dto.card.AddCardRequestDto;
import zw.co.codehive.transactiondesignpatterns.dto.card.ChangePinRequestDto;
import zw.co.codehive.transactiondesignpatterns.service.CarService;
import zw.co.codehive.transactiondesignpatterns.service.CardService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/card")
@Slf4j
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final CarService carService;


    @PostMapping
    public ApiResponse registerCard(@RequestBody AddCardRequestDto requestDto) throws IllegalAccessException, JsonProcessingException {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        var card = cardService.save(requestDto);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),
                new CardResponse(card.getPan(), card.getOrganisation().name(),card.getStatus(),card.getBalance(),
                        card.getIsBlocked(),card.getIsActive(),card.getIsCancelled(),card.getCars()));
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<Card>> getCards(@PathVariable("page") int page,
                                               @PathVariable("size") int size) {
        return ResponseEntity.ok(cardService.getAllCards(PageRequest.of(page, size)));
    }


    @PutMapping("/edit")
    public ResponseEntity<GenericResponse> ChangePin(@RequestParam("cardId") Long cardId,
                                                     @RequestBody ChangePinRequestDto requestDto) throws IllegalAccessException {
        cardService.getCard(cardId);
        cardService.changePin(requestDto);
        return ResponseEntity.ok(new GenericResponse("Record updated successfully"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<GenericResponse> deleteCard(@PathVariable("id") Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok(new GenericResponse("Record deleted successfully"));
    }

    @GetMapping("/pan")
    public ApiResponse findByPan(@RequestParam("cardPan") String pan) {
       var card = cardService.findByPan(pan);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),
                new CardResponse(card.getPan(), card.getOrganisation().name(),card.getStatus(),card.getBalance(),
                        card.getIsBlocked(),card.getIsActive(),card.getIsCancelled(),card.getCars()));
    }

    @GetMapping("/organisation")
    public ApiResponse findByOrganisation(@RequestParam("organisation") String organisation) {
      var cards = cardService.findAllByOrganisation(organisation);

     var cardList =  cards.stream().map(card ->  new CardResponse(card.getPan(), card.getOrganisation().name(),card.getStatus(),card.getBalance(),
              card.getIsBlocked(),card.getIsActive(),card.getIsCancelled(),card.getCars()));

     return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),cardList);
    }


    @GetMapping("/account-number")
    public ApiResponse findByAccountNumber(@RequestParam("accountNumber") String accountNumber) {
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),cardService.findByAccount_AccountNumber(accountNumber));
    }

    @PutMapping("/block")
    public ResponseEntity<GenericResponse> blockCard(@RequestParam("pan") String pan) {
        var card = cardService.findByPan(pan);

        cardService.blockCard(card.getPan());

        return ResponseEntity.ok(new GenericResponse("Record updated successfully"));
    }

    @PutMapping("/cancel")
    public ResponseEntity<GenericResponse> cancelCard(@RequestParam("pan") String pan) {
        var card = cardService.findByPan(pan);

        cardService.cancelCard(card.getPan());

        return ResponseEntity.ok(new GenericResponse("Record updated successfully"));
    }

    @PutMapping("/unblock")
    public ResponseEntity<GenericResponse> unblockCard(@RequestParam("pan") String pan) throws IllegalAccessException {
        var card = cardService.findByPan(pan);

        cardService.unblockCard(card.getPan());
        return ResponseEntity.ok(new GenericResponse("Record updated successfully"));
    }

    @PutMapping("/remove-car")
    public ResponseEntity<GenericResponse> removeCar(@RequestParam("cardPan") String pan,
                                                     @RequestParam ("regNumber") String registrationNumber)  {
        var card = cardService.findByPan(pan);

        var car = carService.getCar(registrationNumber);

        cardService.removeCar(card,car);

        return ResponseEntity.ok(new GenericResponse("Car removed successfully"));
    }

    @GetMapping("cars/{page}/{size}")
    public ResponseEntity<Page<Card>> findAllByCars(List<Card> cards,@PathVariable("page") int page,
                                               @PathVariable("size") int size) {
        return ResponseEntity.ok(cardService.findAllByCarsIn(cards,PageRequest.of(page, size)));
    }

    @GetMapping("active/{page}/{size}")
    public ResponseEntity<Page<Card>> findAllByActive(Boolean active,@PathVariable("page") int page,
                                               @PathVariable("size") int size) {
        return ResponseEntity.ok(cardService.findAllByIsActive(active,PageRequest.of(page, size)));
    }

    @GetMapping("all-blocked/{page}/{size}")
    public ResponseEntity<Page<Card>> findAllByBlocked(Boolean blocked,@PathVariable("page") int page,
                                               @PathVariable("size") int size) {
        return ResponseEntity.ok(cardService.findAllByIsBlocked(blocked,PageRequest.of(page, size)));
    }

    @GetMapping("all-cancelled/{page}/{size}")
    public ResponseEntity<Page<Card>> findAllByCancelled(Boolean cancelled,@PathVariable("page") int page,
                                               @PathVariable("size") int size) {
        return ResponseEntity.ok(cardService.findAllByIsCancelled(cancelled,PageRequest.of(page, size)));
    }

    @GetMapping("all-status-is-active")
    public ApiResponse findAllByPendingStatusAndIsActive(@RequestParam("status") String status) {
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(),cardService.findAllByPendingStatusAndIsActiveFalse(status));
    }
}