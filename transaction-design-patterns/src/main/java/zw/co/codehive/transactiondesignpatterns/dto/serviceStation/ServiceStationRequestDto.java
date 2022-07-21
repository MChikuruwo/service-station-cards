package zw.co.codehive.transactiondesignpatterns.dto.serviceStation;

import lombok.Data;

@Data
public class ServiceStationRequestDto {

    private String stationName;

    private String address;

    private String companyName;
}