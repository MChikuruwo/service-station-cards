package zw.co.jugaad.transactiondesignpatterns.dto.transaction;


import lombok.Data;
import zw.co.jugaad.transactiondesignpatterns.enums.EntryType;

import java.math.BigDecimal;

@Data
public class TransactionRequestDto {

    private String fromAccount;

    private String toAccount;

    private BigDecimal amount;

    private EntryType entryType;
}