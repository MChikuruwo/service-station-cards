package zw.co.codehive.transactiondesignpatterns.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.codehive.transactiondesignpatterns.enums.FuelType;
import zw.co.codehive.transactiondesignpatterns.enums.Transmission;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {
    private String registrationNumber;
    private String make;
    private String model;
    private Transmission transmission;
    private FuelType fuelType;
}
