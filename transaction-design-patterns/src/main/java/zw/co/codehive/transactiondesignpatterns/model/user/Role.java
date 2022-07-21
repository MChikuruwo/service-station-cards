package zw.co.codehive.transactiondesignpatterns.model.user;

import lombok.*;
import zw.co.codehive.commons.dto.AbstractAuditingEntity;

import javax.persistence.*;

@Entity
@Table(schema = "fudzo_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
public class Role extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}