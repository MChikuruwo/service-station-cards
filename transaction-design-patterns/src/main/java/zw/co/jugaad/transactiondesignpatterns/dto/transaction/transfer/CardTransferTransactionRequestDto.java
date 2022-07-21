package zw.co.jugaad.transactiondesignpatterns.dto.transaction.transfer;

import lombok.Data;
import zw.co.jugaad.transactiondesignpatterns.dto.transaction.TransactionRequestDto;
import zw.co.jugaad.transactiondesignpatterns.enums.EntryType;
import zw.co.jugaad.transactiondesignpatterns.enums.TransactionType;

import java.math.BigDecimal;

@Data
public class CardTransferTransactionRequestDto {


    private String fromAccount;

    private String cardPan;

    private BigDecimal amount;

    private EntryType entryType;

    private TransactionType transactionType;
}