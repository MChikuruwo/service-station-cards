package zw.co.codehive.transactiondesignpatterns.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Car;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardResponse {
    private String pan;
    private String organisation;
    private String status;
    public BigDecimal balance;
    private Boolean isBlocked;
    private Boolean isActive;
    private Boolean isCancelled;
    private List<Car> cars;
}