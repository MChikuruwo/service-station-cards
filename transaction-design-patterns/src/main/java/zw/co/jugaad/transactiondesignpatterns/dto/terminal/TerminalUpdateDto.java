package zw.co.jugaad.transactiondesignpatterns.dto.terminal;

import lombok.Data;
import zw.co.jugaad.transactiondesignpatterns.enums.TerminalStatus;
import zw.co.jugaad.transactiondesignpatterns.enums.TerminalType;

@Data
public class TerminalUpdateDto {
    private TerminalStatus terminalStatus;

    private TerminalType terminalType;

    private String serviceStationName;
}
