package zw.co.codehive.transactiondesignpatterns.model.fudzo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.codehive.transactiondesignpatterns.enums.Attendance;
import zw.co.codehive.transactiondesignpatterns.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "attendant")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Attendant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attendant_id")
    private String attendantId;

    @Column(name = "first_name", length = 50)
    private String firstName;

    @Column(name = "last_name",length = 50)
    private String lastName;

    @Column(name = "email",length = 50)
    @Email
    private String email;

    @Column(name = "mobile",length = 20)
    private String mobile;

    @Column(name = "role",length = 10)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "attendance", length = 10)
    @Enumerated(EnumType.STRING)
    private Attendance attendance;

    @ManyToOne
    private ServiceStation serviceStation;

    @Column(name = "user_id")
    private String userId;
}