package zw.co.codehive.transactiondesignpatterns.model.fudzo;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.List;

@Entity
@Table(name = "account_manager")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name",length = 50)
    private String firstname;

    @Column(name = "surname",length = 50)
    private String surname;

    @Column(name = "email",length = 50)
    @Email
    private String email;

    @Column(name ="mobile", length = 20)
    private String mobile;

    @OneToOne
    private Owner owner;

    @OneToMany
    private List<Account> companyAccount;
}