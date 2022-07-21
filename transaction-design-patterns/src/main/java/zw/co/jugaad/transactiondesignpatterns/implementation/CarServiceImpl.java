package zw.co.jugaad.transactiondesignpatterns.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.jugaad.transactiondesignpatterns.dto.serviceStation.CarRequestDto;
import zw.co.jugaad.transactiondesignpatterns.exception.BusinessValidationException;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Car;
import zw.co.jugaad.transactiondesignpatterns.repository.fudzo.CarRepository;
import zw.co.jugaad.transactiondesignpatterns.service.CarService;
import zw.co.jugaad.transactiondesignpatterns.service.CardService;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CardService cardService;
    private final CarRepository carRepository;

    @Override
    public Car save(CarRequestDto requestDto) {
        var card = cardService.findByPan(requestDto.getPan());
        var car = Car.builder()
                .card(card)
                .make(requestDto.getMake())
                .model(requestDto.getModel())
                .transmission(requestDto.getTransmission())
                .registrationNumber(requestDto.getRegistrationNumber())
                .fuelType(requestDto.getFuelType())
                .build();

        cardService.addCars(card,car);

        return carRepository.save(car);
    }

    @Override
    public void update(CarRequestDto requestDto) {
        //TODO gather what's needed to update a vehicle

    }

    @Override
    public Page<Car> getAllCars(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    @Override
    public Car getCar(String registrationNumber) {
        return getOne(registrationNumber);
    }

    @Override
    public void deleteCar(String registrationNumber) {
        var car = getOne(registrationNumber);

        if (car == null)
            throw new BusinessValidationException("Vehicle with Reg Number: " + registrationNumber + " not found");

        carRepository.delete(car);
    }

    @Override
    public Car getOne(String registrationNumber) {
        var car = carRepository.findByRegistrationNumber(registrationNumber);

        if (car == null)
            throw new BusinessValidationException("Vehicle with Reg Number: " + registrationNumber + " not found");

        return car;
    }

    @Override
    public List<Car> findCarsByCard(String cardPan) {
        var cars = carRepository.findCarsByCard(cardService.findByPan(cardPan));
        return cars;
    }
}