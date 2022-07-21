package zw.co.jugaad.transactiondesignpatterns.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerminalResponseDto {
    private String terminalId;
    private String imei;
    private String terminalType;
    private String status;
    private String serviceStationId;
}