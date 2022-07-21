package zw.co.codehive.transactiondesignpatterns.dto.card;

import lombok.Data;

@Data
public class ChangePinRequestDto {
    private String pan;

    private String pin;
}