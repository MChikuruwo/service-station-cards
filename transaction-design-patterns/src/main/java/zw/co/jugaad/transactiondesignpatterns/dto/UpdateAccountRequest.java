package zw.co.jugaad.transactiondesignpatterns.dto;

import lombok.Data;

@Data
public class UpdateAccountRequest {
    private String accountNumber;
    private String cardPan;
}