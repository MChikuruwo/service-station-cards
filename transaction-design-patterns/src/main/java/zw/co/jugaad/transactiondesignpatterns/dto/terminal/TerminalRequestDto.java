package zw.co.jugaad.transactiondesignpatterns.dto.terminal;


import lombok.Data;
import zw.co.jugaad.transactiondesignpatterns.enums.Status;
import zw.co.jugaad.transactiondesignpatterns.enums.TerminalType;

@Data
public class TerminalRequestDto {

    private String imei;

    private TerminalType terminalType;

    private String serviceStationName;
}