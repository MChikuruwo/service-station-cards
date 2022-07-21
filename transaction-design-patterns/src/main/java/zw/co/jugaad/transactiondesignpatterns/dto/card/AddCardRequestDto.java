package zw.co.jugaad.transactiondesignpatterns.dto.card;


import lombok.Data;
import zw.co.jugaad.transactiondesignpatterns.enums.Organisation;

import java.math.BigDecimal;

@Data
public class AddCardRequestDto {

    private String pan;

    private Organisation organisation;

    private String pin;

    private BigDecimal balance;
}