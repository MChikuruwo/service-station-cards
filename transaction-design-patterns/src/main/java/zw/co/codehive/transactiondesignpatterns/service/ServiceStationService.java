package zw.co.codehive.transactiondesignpatterns.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.codehive.transactiondesignpatterns.dto.serviceStation.ServiceStationRequestDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.ServiceStation;

public interface ServiceStationService {

    ServiceStation save(ServiceStationRequestDto requestDto);

    void update(ServiceStationRequestDto requestDto);

    Page<ServiceStation> getAllServiceStations(Pageable pageable);

    ServiceStation getServiceStation(Long id);

    void deleteServiceStation(Long id);

    ServiceStation getOne(Long id);

    ServiceStation findByStationName(String serviceStationName);
}