package zw.co.codehive.transactiondesignpatterns.dto.transaction.transfer;

import lombok.Data;
import zw.co.codehive.transactiondesignpatterns.enums.EntryType;
import zw.co.codehive.transactiondesignpatterns.enums.TransactionType;

import java.math.BigDecimal;

@Data
public class CardTransferTransactionRequestDto {


    private String fromAccount;

    private String cardPan;

    private BigDecimal amount;

    private EntryType entryType;

    private TransactionType transactionType;
}