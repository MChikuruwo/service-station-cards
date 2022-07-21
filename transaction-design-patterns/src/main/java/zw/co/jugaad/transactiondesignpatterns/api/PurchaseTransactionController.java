package zw.co.jugaad.transactiondesignpatterns.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import zw.co.jugaad.fudzocommons.api.ApiResponse;
import zw.co.jugaad.transactiondesignpatterns.dto.transaction.TransactionResponse;
import zw.co.jugaad.transactiondesignpatterns.dto.transaction.purchase.PurchaseRequestDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.DepositTransaction;
import zw.co.jugaad.transactiondesignpatterns.service.PurchaseTransactionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/secure/purchase")
public class PurchaseTransactionController {

    private final PurchaseTransactionService purchaseTransactionService;

    @PostMapping("/transact")
    public ApiResponse transact(@RequestBody PurchaseRequestDto depositRequestDto) {

        var transaction = new DepositTransaction();

        purchaseTransactionService.transact(depositRequestDto);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),
                new TransactionResponse(depositRequestDto.getFromAccount(), depositRequestDto.getToAccount(),
                        depositRequestDto.getAmount(), depositRequestDto.getEntryType(), transaction.getTransactionReference()));
    }

    @PutMapping("/reverse")
    public ApiResponse reversal(@RequestParam("reference") String reference) {

        purchaseTransactionService.reverse(reference);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString());
    }


    @PutMapping("/validate")
    public ApiResponse validate(@RequestParam("reference") String reference) {

        purchaseTransactionService.validate(reference);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString());
    }

    @PutMapping("/cancel")
    public ApiResponse cancel(@RequestParam("reference") String reference) {

        purchaseTransactionService.cancel(reference);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString());
    }

    @GetMapping("/pending-validation")
    public ApiResponse getAllPendingValidation() {

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(), purchaseTransactionService.getAllPendingValidation());
    }
}
