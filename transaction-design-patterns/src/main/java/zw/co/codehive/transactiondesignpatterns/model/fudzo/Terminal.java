package zw.co.codehive.transactiondesignpatterns.model.fudzo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.codehive.transactiondesignpatterns.enums.TerminalStatus;
import zw.co.codehive.transactiondesignpatterns.enums.TerminalType;

import javax.persistence.*;

@Entity
@Table(name = "terminal")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "terminalId", unique = true, length = 30)
    private String terminalId;

    @Column(name = "imei", unique = true, length = 15)
    private String imei;

    @Enumerated(EnumType.STRING)
    @Column(name = "terminal_type", length = 20)
    private TerminalType terminalType;

    @Enumerated(EnumType.STRING)
    @Column(name = "terminal_status", length = 20)
    private TerminalStatus status = TerminalStatus.ONLINE;

    @ManyToOne
    private ServiceStation serviceStation;
}