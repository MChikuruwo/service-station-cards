package zw.co.codehive.transactiondesignpatterns.dto.transaction.purchase;

import lombok.Data;
import zw.co.codehive.transactiondesignpatterns.dto.transaction.TransactionRequestDto;

@Data
public class PurchaseRequestDto extends TransactionRequestDto {

    private String cardPan;

    private String productName;

    private String attendantId;

    private String terminalId;

    private String pin;
}