package zw.co.codehive.transactiondesignpatterns.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import zw.co.codehive.commons.api.ApiResponse;
import zw.co.codehive.transactiondesignpatterns.dto.transaction.transfer.CardTransferTransactionRequestDto;
import zw.co.codehive.transactiondesignpatterns.dto.transaction.transfer.CardTransferTransactionResponseDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.CardTransferTransaction;
import zw.co.codehive.transactiondesignpatterns.service.CardTransferTransactionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/secure/card")
public class CardTransferController {

    private final CardTransferTransactionService cardTransferTransactionService;

    @PostMapping("/transact")
    public ApiResponse transact(@RequestBody CardTransferTransactionRequestDto requestDto) {

        var transaction = new CardTransferTransaction();


        cardTransferTransactionService.transact(requestDto);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),
                new CardTransferTransactionResponseDto(requestDto.getFromAccount(), requestDto.getCardPan(),
                        requestDto.getAmount(), requestDto.getEntryType(), transaction.getTransactionReference()));
    }

    @PutMapping("/reverse")
    public ApiResponse reversal(@RequestParam("reference") String reference) {

        cardTransferTransactionService.reverse(reference);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString());

    }


    @PutMapping("/validate")
    public ApiResponse validate(@RequestParam("reference") String reference) {

        cardTransferTransactionService.validate(reference);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString());

    }

    @PutMapping("/cancel")
    public ApiResponse cancel(@RequestParam("reference") String reference) {

        cardTransferTransactionService.cancel(reference);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString());
    }

    @GetMapping("/pending-validation")
    public ApiResponse getAllPendingValidation() {

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(), cardTransferTransactionService.getAllPendingValidation());
    }
}
