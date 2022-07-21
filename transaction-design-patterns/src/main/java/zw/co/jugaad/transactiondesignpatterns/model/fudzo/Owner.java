package zw.co.jugaad.transactiondesignpatterns.model.fudzo;


import lombok.*;
import zw.co.jugaad.fudzocommons.dto.AbstractAuditingEntity;
import zw.co.jugaad.fudzocommons.enums.Organisation;
import zw.co.jugaad.transactiondesignpatterns.enums.Role;
import zw.co.jugaad.transactiondesignpatterns.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "owner")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Owner extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_id")
    private String ownerId;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name",length = 50)
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Organisation organisation;

    @Column(name = "email",length = 50)
    @Email
    private String email;

    @Column(name = "mobile",length = 20)
    private String mobile;

    @Column(name = "role",length = 10)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "company_name", length = 50, unique = true)
    private String companyName;

    @Column(name = "user_id")
    private String userId;
}