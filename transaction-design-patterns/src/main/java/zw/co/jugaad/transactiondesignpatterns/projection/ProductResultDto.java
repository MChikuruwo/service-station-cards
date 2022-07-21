package zw.co.jugaad.transactiondesignpatterns.projection;

import java.math.BigDecimal;

public interface ProductResultDto {

    Long getId();

    String getProductName();

    BigDecimal getPrice();

    Long getStationId();

    String getServiceName();
}
