package zw.co.codehive.transactiondesignpatterns.dto.terminal;

import lombok.Data;


@Data
public class LaunchResponseDto {

    private String key;

    private DeviceDetail deviceDetail;
}