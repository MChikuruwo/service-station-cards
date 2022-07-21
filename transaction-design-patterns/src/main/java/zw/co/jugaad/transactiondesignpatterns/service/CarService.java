package zw.co.jugaad.transactiondesignpatterns.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.jugaad.transactiondesignpatterns.dto.serviceStation.CarRequestDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Car;

import java.util.List;

public interface CarService {

    Car save(CarRequestDto requestDto) ;

    void update(CarRequestDto requestDto);

    Page<Car> getAllCars(Pageable pageable);

    Car getCar(String registrationNumber);

    void deleteCar(String registrationNumber);

    Car getOne(String registrationNumber);

    List<Car> findCarsByCard(String cardPan);

}