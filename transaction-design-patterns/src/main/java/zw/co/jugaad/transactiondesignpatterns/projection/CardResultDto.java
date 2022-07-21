package zw.co.jugaad.transactiondesignpatterns.projection;

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
