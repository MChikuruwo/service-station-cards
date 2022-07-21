package zw.co.codehive.transactiondesignpatterns.model.fudzo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import zw.co.codehive.transactiondesignpatterns.exception.InsufficientFundsException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "AccountType")
@Data
@Builder
@AllArgsConstructor
public class Account {

    @Id
    @Column(length = 16)
    public String accountNumber;


    @Column(name = "balance")
    public BigDecimal balance;


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Card> cards;

    public Account(BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Account() {

    }

    protected void deposit(BigDecimal amount) {
        this.balance = balance.add(amount);
    }


    public void withdraw(BigDecimal amount) throws InsufficientFundsException {
        if (balance.compareTo(amount) > 0) {
            throw new InsufficientFundsException("Account balance is not enough.  Amount " + amount + "above current balance of " + balance);
        }
        this.balance = balance.subtract(amount);
    }


    protected void adjustment(BigDecimal amount) {
        this.balance = balance.subtract(amount);
    }


    protected void topup(BigDecimal amount) {

    }
}
