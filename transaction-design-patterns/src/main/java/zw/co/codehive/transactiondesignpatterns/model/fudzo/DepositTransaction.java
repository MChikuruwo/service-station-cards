package zw.co.codehive.transactiondesignpatterns.model.fudzo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "deposit_transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DepositTransaction extends Transaction {

    @ManyToOne
    private Admin initiator;//to be taken from security context

}