package zw.co.jugaad.transactiondesignpatterns.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.jugaad.transactiondesignpatterns.dto.account.AccountManagerRequestDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.AccountManager;
import zw.co.jugaad.transactiondesignpatterns.response.GenericResponse;
import zw.co.jugaad.transactiondesignpatterns.service.AccountManagerService;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/account-manager")
@Slf4j
public class AccountManagerController {

    private final AccountManagerService accountManagerService;

    public AccountManagerController(AccountManagerService accountManagerService) {
        this.accountManagerService = accountManagerService;
    }

    @PostMapping
    public ResponseEntity<AccountManager> registerAccountManager(@RequestBody AccountManagerRequestDto requestDto) throws IllegalAccessException, JsonProcessingException {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri)
                .body(accountManagerService.save(requestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountManager> getAccountManager(@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountManagerService.getAccountManager(id));
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<AccountManager>> getAccountManager(@PathVariable("page") int page,
                                                                  @PathVariable("size") int size) {
        return ResponseEntity.ok(accountManagerService.getAllAccountManagers(PageRequest.of(page, size)));
    }


    @PutMapping("/edit")
    public ResponseEntity<GenericResponse> editAccountManager(@RequestParam("accountManager-id") Long accountManagerId,
                                                              @RequestBody AccountManagerRequestDto requestDto) throws IllegalAccessException {
        accountManagerService.getOne(accountManagerId);
        accountManagerService.update(requestDto);
        return ResponseEntity.ok(new GenericResponse("Record updated successfully"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteManager(@PathVariable("id") Long id) {
        accountManagerService.deleteAccountManager(id);
        return ResponseEntity.ok(new GenericResponse("Record deleted successfully"));
    }

}