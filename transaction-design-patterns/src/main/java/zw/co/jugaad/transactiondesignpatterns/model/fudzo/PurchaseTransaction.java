package zw.co.jugaad.transactiondesignpatterns.model.fudzo;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PurchaseTransaction extends Transaction{

    @Column(name = "litres")
    private BigDecimal litres;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Attendant attendant;

    @ManyToOne
    private Terminal terminal;

    @ManyToOne(fetch = FetchType.LAZY)
    private Card card;

    private String pin;
}