package zw.co.codehive.transactiondesignpatterns.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.zalando.problem.spring.web.advice.ProblemHandling;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
@RestController

public class CustomExceptionHandler implements ProblemHandling {


    @ExceptionHandler(BusinessValidationException.class)
    public final ResponseEntity<RestResponse> handleIllegalOperationException(BusinessValidationException ex, WebRequest request) {
        RestResponse errorDetails = new RestResponse(ex.getMessage(), LocalDateTime.now().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FraudDetectedException.class)
    public final ResponseEntity<RestResponse> handleIllegalOperationException(FraudDetectedException ex, WebRequest request) {
        RestResponse errorDetails = new RestResponse(ex.getMessage(), LocalDateTime.now().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
