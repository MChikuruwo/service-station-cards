package zw.co.jugaad.transactiondesignpatterns.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceStationResponseDto {
    private String stationName;
    private String address;
    private String contactNumber;
}