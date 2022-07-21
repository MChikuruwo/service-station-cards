package zw.co.codehive.transactiondesignpatterns.model.fudzo;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import java.math.BigDecimal;


@Entity
@Table(name = "company_account")
@DiscriminatorValue("Company")
@Data
@AllArgsConstructor
public class CompanyAccount extends Account {

    @Column(name = "company_name",unique = true, length = 50)
    private String companyName;

    @Email
    @Column(name = "email",length = 50)
    private String email;

    @Column(name ="contact_number", length = 30)
    private String contactNumber;

    @Column(name = "registration_number",unique = true, length = 30)
    private String registrationNumber;

    public CompanyAccount() {
        super(BigDecimal.ZERO);
    }
}