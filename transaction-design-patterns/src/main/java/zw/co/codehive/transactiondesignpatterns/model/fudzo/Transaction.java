package zw.co.codehive.transactiondesignpatterns.model.fudzo;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import zw.co.codehive.transactiondesignpatterns.enums.Currency;
import zw.co.codehive.transactiondesignpatterns.enums.EntryType;
import zw.co.codehive.transactiondesignpatterns.enums.TransactionStatus;
import zw.co.codehive.transactiondesignpatterns.enums.TransactionType;
import zw.co.jugaad.transactiondesignpatterns.enums.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account fromAccount;

    @ManyToOne
    private Account toAccount;

    @Column(name="transaction_reference",unique = true)
    private  String transactionReference;

    @Column(name = "amount")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "entry_type")
    private EntryType entryType;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private Currency currency = Currency.USD;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status")
    private TransactionStatus transactionStatus=TransactionStatus.PENDING;

    @Column(name = "reversed")
    private Boolean reversed = Boolean.FALSE;

    @Column(name = "is_approved")
    private Boolean isApproved=false;

    @Column(name = "is_cancelled")
    private Boolean isCancelled=false;

    @CreationTimestamp
    @Column(name = "date_created")
    private LocalDateTime dateCreated;
}