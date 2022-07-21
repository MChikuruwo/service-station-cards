package zw.co.codehive.transactiondesignpatterns.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CardResultDto {

     String getPan();

     String getStatus();

     LocalDateTime getActivatedOn();

     LocalDateTime getBloeckedOn();

     LocalDateTime getCancelledOn();

     BigDecimal getBalance();

     String getAccount();
}
