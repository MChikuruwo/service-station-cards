package zw.co.jugaad.transactiondesignpatterns.dto.product;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {

    private String productName;

    private BigDecimal price;

    private String stationName;

    private  BigDecimal quantity;
}