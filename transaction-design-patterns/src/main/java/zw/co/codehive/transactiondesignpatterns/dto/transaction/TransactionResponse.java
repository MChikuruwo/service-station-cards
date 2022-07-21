package zw.co.codehive.transactiondesignpatterns.dto.transaction;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.codehive.transactiondesignpatterns.enums.EntryType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private String fromAccount;

    private String toAccount;

    private BigDecimal amount;

    private EntryType entryType;

    public String transactionReference;
}