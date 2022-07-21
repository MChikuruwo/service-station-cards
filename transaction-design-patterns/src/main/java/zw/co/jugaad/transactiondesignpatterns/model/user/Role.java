package zw.co.jugaad.transactiondesignpatterns.model.user;

import lombok.*;
import zw.co.jugaad.fudzocommons.dto.AbstractAuditingEntity;

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