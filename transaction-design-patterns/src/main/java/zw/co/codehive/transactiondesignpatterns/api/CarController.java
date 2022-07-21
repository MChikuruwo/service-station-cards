package zw.co.codehive.transactiondesignpatterns.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.codehive.commons.api.ApiResponse;
import zw.co.codehive.transactiondesignpatterns.dto.response.CarResponse;
import zw.co.codehive.transactiondesignpatterns.dto.serviceStation.CarRequestDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Car;
import zw.co.codehive.transactiondesignpatterns.response.GenericResponse;
import zw.co.codehive.transactiondesignpatterns.service.CarService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/car")
@Slf4j
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ApiResponse registerCar(@RequestBody CarRequestDto requestDto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        var car = carService.save(requestDto);
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),
                new CarResponse(car.getRegistrationNumber(), car.getMake(), car.getModel(), car.getTransmission(), car.getFuelType()));
    }

    @GetMapping("/reg")
    public ApiResponse getCar(@RequestParam("registrationNumber") String registrationNumber) {
       var car = carService.getCar(registrationNumber);
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(),
                new CarResponse(car.getRegistrationNumber(), car.getMake(), car.getModel(), car.getTransmission(), car.getFuelType()));
    }

    @GetMapping("/card")
    public ResponseEntity<List<Car>> getCarsByCard(@RequestParam("cardPan") String cardPan) {
        return ResponseEntity.ok(carService.findCarsByCard(cardPan));
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<Car>> getCars(@PathVariable("page") int page,
                                             @PathVariable("size") int size) {
        return ResponseEntity.ok(carService.getAllCars(PageRequest.of(page, size)));

    }


    @PutMapping("/edit")
    public ResponseEntity<GenericResponse> editCar(@RequestParam("registrationNumber") String registrationNumber,
                                                   @RequestBody CarRequestDto carRequestDto) throws IllegalAccessException {
        carService.getCar(registrationNumber);
        carService.update(carRequestDto);
        return ResponseEntity.ok(new GenericResponse("Record updated successfully"));
    }


    @DeleteMapping("/reg-number")
    public ResponseEntity<GenericResponse> deleteCar(@RequestParam("registrationNumber") String registrationNumber) {
        carService.deleteCar(registrationNumber);
        return ResponseEntity.ok(new GenericResponse("Record deleted successfully"));
    }

}