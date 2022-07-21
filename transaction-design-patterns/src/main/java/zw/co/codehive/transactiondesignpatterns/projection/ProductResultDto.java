package zw.co.codehive.transactiondesignpatterns.projection;

import java.math.BigDecimal;

public interface ProductResultDto {

    Long getId();

    String getProductName();

    BigDecimal getPrice();

    Long getStationId();

    String getServiceName();
}
