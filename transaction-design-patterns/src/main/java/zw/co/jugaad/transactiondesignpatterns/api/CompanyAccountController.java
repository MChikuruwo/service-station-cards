package zw.co.jugaad.transactiondesignpatterns.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.jugaad.fudzocommons.api.ApiResponse;
import zw.co.jugaad.fudzocommons.exceptions.BusinessValidationException;
import zw.co.jugaad.transactiondesignpatterns.dto.UpdateAccountRequest;
import zw.co.jugaad.transactiondesignpatterns.dto.account.AccountRequestDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Account;
import zw.co.jugaad.transactiondesignpatterns.response.GenericResponse;
import zw.co.jugaad.transactiondesignpatterns.service.AccountService;
import zw.co.jugaad.transactiondesignpatterns.service.CardService;

import javax.annotation.Resource;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/company")
@Slf4j
public class CompanyAccountController {

    private final AccountService accountService;

    @Resource
    private  CardService cardService;

    public CompanyAccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping
    public ResponseEntity<Account> registerAccount(@RequestBody AccountRequestDto requestDto) throws IllegalAccessException, JsonProcessingException, BusinessValidationException {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri)
                .body(accountService.saveCompanyAccount(requestDto));
    }

    @GetMapping("/accountNumber")
    public ResponseEntity<Account> getAccount(@RequestParam("accountNumber") String accountNumber) {
        return ResponseEntity.ok(accountService.getAccount(accountNumber));
    }

    @GetMapping("/card")
    public ApiResponse getAccountByCardPan(@RequestParam("pan") String pan) {
        return new ApiResponse(HttpStatus.OK.value(),HttpStatus.OK.name(),accountService.findAccountByCardPan(pan));
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
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),"Record updated successfully");
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<GenericResponse> deleteAccount(@PathVariable("accountNumber") String accountNumber) {
        accountService.deleteAccount(accountNumber);
        return ResponseEntity.ok(new GenericResponse("Record deleted successfully"));
    }

}