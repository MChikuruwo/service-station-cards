package zw.co.codehive.transactiondesignpatterns.dto.serviceStation;

import lombok.Data;
import zw.co.codehive.transactiondesignpatterns.enums.FuelType;
import zw.co.codehive.transactiondesignpatterns.enums.Transmission;

import javax.validation.constraints.NotBlank;

@Data
public class CarRequestDto {

    @NotBlank(message = "The make of the car cannot be null")
    private String make;

    @NotBlank(message = "The model of the car cannot be null")
    private String model;

    @NotBlank(message = "The registration number of the car cannot be null")
    private String registrationNumber;

    @NotBlank(message = "The transmission type(PETROL/DIESEL) of the car cannot be null")
    private Transmission transmission;

    @NotBlank(message = "The transmission type(PETROL/DIESEL) of the car cannot be null")
    private FuelType fuelType;

    private String pan;
}