package zw.co.jugaad.userservice.model;

import lombok.*;
import zw.co.jugaad.fudzocommons.dto.AbstractAuditingEntity;
import zw.co.jugaad.fudzocommons.enums.Organisation;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class User extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String userId;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Organisation organisation;

    @Column(unique = true)
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "invalid.email.address")
    private String emailAddress;


    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9]{5,29}+$", message = "username.must.be.of.length.5")
    private String userName;

    @Column(unique = true)
    @Pattern(regexp = "^\\+[1-9]{1}[0-9]{3,14}+$", message = "invalid.mobile.number")
    private String mobileNumber;

    private String password;

    private boolean isSupervisor;

    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> role;
}