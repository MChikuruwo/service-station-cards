package zw.co.codehive.transactiondesignpatterns.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import zw.co.codehive.commons.api.ApiResponse;
import zw.co.codehive.transactiondesignpatterns.dto.transaction.TransactionRequestDto;
import zw.co.codehive.transactiondesignpatterns.dto.transaction.TransactionResponse;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.DepositTransaction;
import zw.co.codehive.transactiondesignpatterns.service.DepositTransactionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/secure/deposit")
public class DepositTransactionController {

    private final DepositTransactionService depositTransaction;

//    private final AdminService adminService;

    @PostMapping("/transact")
    public ApiResponse transact(/*@RequestParam("initiatorUserName") String initiatorUserName,*/
                                   @RequestBody TransactionRequestDto depositRequestDto) {

        var transaction = new DepositTransaction();

//        var initiator = adminService.findByUserName(initiatorUserName);

//        transaction.setInitiator(initiator.get());

        depositTransaction.transact(depositRequestDto);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),
                new TransactionResponse(depositRequestDto.getFromAccount(), depositRequestDto.getToAccount(),
                        depositRequestDto.getAmount(),depositRequestDto.getEntryType(),transaction.getTransactionReference()));
    }

    @PutMapping("/reverse")
    public ApiResponse reversal(@RequestParam("reference") String reference){

        depositTransaction.reverse(reference);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString());

    }


    @PutMapping("/validate")
    public ApiResponse validate(@RequestParam("reference") String reference){

        depositTransaction.validate(reference);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString());

    }

    @PutMapping("/cancel")
    public ApiResponse cancel(@RequestParam("reference") String reference){

        depositTransaction.cancel(reference);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString());
    }

    @GetMapping("/pending-validation")
    public ApiResponse getAllPendingValidation(){

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),depositTransaction.getAllPendingValidation());
    }
}