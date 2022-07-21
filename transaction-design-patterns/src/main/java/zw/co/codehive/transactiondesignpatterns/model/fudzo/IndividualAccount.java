package zw.co.codehive.transactiondesignpatterns.model.fudzo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Entity
@Table(name = "individual_account")
@DiscriminatorValue("Individual")
@Data
@AllArgsConstructor
public class IndividualAccount extends Account {

    @Column(name = "first_name",length = 50)
    private String firstname;

    @Column(name = "last_name",length = 50)
    private String lastname;

    @Email
    @Column(name="email",length = 50)
    private String email;

    @Column(name = "mobile", length = 30)
    private String mobile;

    @Column(name = "id_number",unique = true, length = 15)
    @Pattern(regexp = "^[0-9]{2}-[0-9]{6,7}[A-Z]{1}[0-9]{2}$",message = "please.enter.valid.national.id.number")
    private String idNumber;

    public IndividualAccount() {
        super(BigDecimal.ZERO);
    }
}