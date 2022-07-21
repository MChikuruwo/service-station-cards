package zw.co.codehive.transactiondesignpatterns.dto.terminal;


import lombok.Data;
import zw.co.codehive.transactiondesignpatterns.enums.TerminalType;

@Data
public class TerminalRequestDto {

    private String imei;

    private TerminalType terminalType;

    private String serviceStationName;
}