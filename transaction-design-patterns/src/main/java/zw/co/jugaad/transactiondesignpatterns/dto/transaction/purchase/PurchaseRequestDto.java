package zw.co.jugaad.transactiondesignpatterns.dto.transaction.purchase;

import lombok.Data;
import zw.co.jugaad.transactiondesignpatterns.dto.transaction.TransactionRequestDto;

import java.math.BigDecimal;

@Data
public class PurchaseRequestDto extends TransactionRequestDto {

    private String cardPan;

    private String productName;

    private String attendantId;

    private String terminalId;

    private String pin;
}