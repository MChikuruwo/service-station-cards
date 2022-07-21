package zw.co.jugaad.transactiondesignpatterns.dto.terminal;

import lombok.Data;


@Data
public class LaunchResponseDto {

    private String key;

    private DeviceDetail deviceDetail;
}