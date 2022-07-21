package zw.co.codehive.transactiondesignpatterns.model.fudzo;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "card_transfer_transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CardTransferTransaction extends Transaction {

    @ManyToMany(fetch = FetchType.LAZY)
   private List<Card> card;

    @ManyToOne
    private Admin maker;

    @ManyToOne
    private Admin checker;
}