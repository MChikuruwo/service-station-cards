package zw.co.codehive.transactiondesignpatterns.dto.terminal;

import lombok.Data;
import zw.co.codehive.transactiondesignpatterns.enums.TerminalStatus;
import zw.co.codehive.transactiondesignpatterns.enums.TerminalType;

@Data
public class TerminalUpdateDto {
    private TerminalStatus terminalStatus;

    private TerminalType terminalType;

    private String serviceStationName;
}
