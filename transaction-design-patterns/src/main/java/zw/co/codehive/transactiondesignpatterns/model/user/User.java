package zw.co.codehive.transactiondesignpatterns.model.user;

import lombok.*;
import zw.co.codehive.commons.dto.AbstractAuditingEntity;
import zw.co.codehive.commons.enums.Organisation;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(schema = "fudzo_users",name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id",unique = true)
    private String userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Organisation organisation;

    @Column(name = "email_address",unique = true)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "invalid.email.address")
    private String emailAddress;


    @Column(name = "user_name",unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9]{5,29}+$", message = "username.must.be.of.length.5")
    private String userName;

    @Column(name = "mobile_number",unique = true)
    @Pattern(regexp = "^\\+[1-9]{1}[0-9]{3,14}+$", message = "invalid.mobile.number")
    private String mobileNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "is_supervisor")
    private boolean isSupervisor;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> role;
}