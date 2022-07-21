package zw.co.jugaad.transactiondesignpatterns.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.jugaad.transactiondesignpatterns.enums.FuelType;
import zw.co.jugaad.transactiondesignpatterns.enums.Transmission;

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
