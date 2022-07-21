package zw.co.codehive.transactiondesignpatterns.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.codehive.commons.api.ApiResponse;
import zw.co.codehive.commons.exceptions.BusinessValidationException;
import zw.co.codehive.transactiondesignpatterns.dto.account.IndividualAccountRequestDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Account;
import zw.co.codehive.transactiondesignpatterns.response.GenericResponse;
import zw.co.codehive.transactiondesignpatterns.service.AccountService;
import zw.co.codehive.transactiondesignpatterns.service.CardService;

import javax.annotation.Resource;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/individual")
@Slf4j
public class IndividualAccountController {

    private final AccountService accountService;

    @Resource
    private CardService cardService;

    public IndividualAccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping
    public ResponseEntity<Account> registerAccount(@RequestBody IndividualAccountRequestDto requestDto) throws IllegalAccessException, JsonProcessingException, BusinessValidationException {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri)
                .body(accountService.saveIndividualAccount(requestDto));
    }

    @GetMapping("/accountNumber")
    public ResponseEntity<Account> getAccount(@RequestParam("accountNumber") String accountNumber) {
        return ResponseEntity.ok(accountService.getAccount(accountNumber));
    }

    @GetMapping("/card")
    public ResponseEntity<Account> getAccountByCardPan(@RequestParam("pan") String pan) {
        return ResponseEntity.ok(accountService.findAccountByCardPan(pan));
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<Account>> getAccount(@PathVariable("page") int page,
                                                    @PathVariable("size") int size) {
        return ResponseEntity.ok(accountService.getAllAccounts(PageRequest.of(page, size)));
    }


    @PutMapping("/add-card")
    public ApiResponse addCard(@RequestParam ("accountNumber") String accountNumber,
                               @RequestParam ("cardPan") String cardPan)  {
        var account = accountService.getAccount(accountNumber);
        var card = cardService.findByPan(cardPan);
        accountService.addCard(account,card);
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(), "Record updated successfully");
    }


    @DeleteMapping("/{accountNumber}")
    public ResponseEntity deleteAccount(@PathVariable("accountNumber") String accountNumber) {
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.ok(new GenericResponse("Record deleted successfully"));
    }

}