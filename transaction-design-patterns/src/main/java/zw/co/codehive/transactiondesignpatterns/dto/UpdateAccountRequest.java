package zw.co.codehive.transactiondesignpatterns.dto;

import lombok.Data;

@Data
public class UpdateAccountRequest {
    private String accountNumber;
    private String cardPan;
}