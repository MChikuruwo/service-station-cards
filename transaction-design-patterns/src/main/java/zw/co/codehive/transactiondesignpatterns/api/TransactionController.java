package zw.co.codehive.transactiondesignpatterns.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction")
@Slf4j
public class TransactionController {

    @PostMapping
    public void buyFuel() {

    }
}
