package zw.co.codehive.transactiondesignpatterns.model.fudzo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import zw.co.codehive.commons.dto.AbstractAuditingEntity;
import zw.co.codehive.commons.enums.Organisation;
import zw.co.codehive.transactiondesignpatterns.exception.InsufficientFundsException;
import zw.co.codehive.transactiondesignpatterns.exception.ValidationException;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "card")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 16, message = "Minimum length is 16 digits")
    @Size(max = 16, message = "Maximum length is 16 digits")
//    @LuhnCheck(message = "Card failed Luhn check")
    @Column(length = 16,unique = true)
    private String pan;

    @Enumerated(EnumType.STRING)
    private Organisation organisation;

    private String pin;

    private String status;

    public BigDecimal balance;

    @CreationTimestamp
    private LocalDateTime activatedOn;

    private LocalDateTime blockedOn;

    private LocalDateTime cancelledOn;

    private Boolean isBlocked;

    private Boolean isActive;

    private Boolean isCancelled;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Car> cars;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE})
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "card_id")
    @JsonBackReference
    private Account account;


    private void validate() throws ValidationException {

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

    public void addCar(Car car) {
        this.cars.add(car);
    }

    //remove a specific car
    public void removeCar(String registrationNumber) {
//        Predicate<Item> doesExist = item -> item.exists(registrationNumber);
//        List<Car> cars = this.cars.stream()
//                .filter(doesExist)
//                .forEach(item->item.operate());
//        cars.removeIf(doesExist);
//
    }

    class Item {
        private String value;

        public Item(String value) {
            this.value = value;
        }

        public boolean exists(String registrationNumber) {
            return value.equalsIgnoreCase(registrationNumber);
        }

        public void operate() {

        }
    }
}