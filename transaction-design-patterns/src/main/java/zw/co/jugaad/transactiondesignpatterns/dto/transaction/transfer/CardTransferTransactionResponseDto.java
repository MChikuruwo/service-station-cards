package zw.co.jugaad.transactiondesignpatterns.dto.transaction.transfer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.jugaad.transactiondesignpatterns.enums.EntryType;
import zw.co.jugaad.transactiondesignpatterns.enums.TransactionType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardTransferTransactionResponseDto {

    private String fromAccount;

    private String cardPan;

    private BigDecimal amount;

    private EntryType entryType;

    public String transactionReference;
}
