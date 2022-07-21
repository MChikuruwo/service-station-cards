package zw.co.jugaad.transactiondesignpatterns.implementation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.jugaad.transactiondesignpatterns.dto.serviceStation.ServiceStationRequestDto;
import zw.co.jugaad.transactiondesignpatterns.exception.BusinessValidationException;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Owner;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.ServiceStation;
import zw.co.jugaad.transactiondesignpatterns.repository.fudzo.ServiceStationRepository;
import zw.co.jugaad.transactiondesignpatterns.service.OwnerService;
import zw.co.jugaad.transactiondesignpatterns.service.ServiceStationService;


@Slf4j
@Service
public class ServiceStationServiceImpl implements ServiceStationService {

    private final ServiceStationRepository repository;
    private final OwnerService ownerService;

    public ServiceStationServiceImpl(ServiceStationRepository repository, OwnerService ownerService) {
        this.repository = repository;
        this.ownerService = ownerService;
    }

    @Override
    public ServiceStation save(ServiceStationRequestDto requestDto) {

        var owner = ownerService.findOwnerByCompanyName(requestDto.getCompanyName());

        var serviceStation = ServiceStation.builder()
                .stationName(requestDto.getStationName())
                .address(requestDto.getAddress())
                .contactNumber(owner.getMobile())
                .build();
        serviceStation.addOwner(owner);
        return repository.save(serviceStation);
    }

    @Override
    public void update(ServiceStationRequestDto requestDto) {

        var owner = ownerService.findOwnerByCompanyName(requestDto.getCompanyName());
        var  serviceStation = ServiceStation.builder()
                .stationName(requestDto.getStationName())
                .address(requestDto.getAddress())
                .build();
        serviceStation.addOwner(owner);
        repository.save(serviceStation);
    }

    @Override
    public Page<ServiceStation> getAllServiceStations(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public ServiceStation getServiceStation(Long id) {
        return getOne(id);
    }

    @Override
    public void deleteServiceStation(Long id) {
        var serviceStation = getServiceStation(id);

        if (serviceStation == null)
            throw new BusinessValidationException("Service station with id " + id + " does not exist");

        repository.delete(serviceStation);
    }

    @Override
    public ServiceStation getOne(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new BusinessValidationException("Service station with id " + id + " does not exist")
        );
    }

    @Override
    public ServiceStation findByStationName(String serviceStationName) {
        var serviceStation = repository.findByStationName(serviceStationName);

        if(serviceStation.isEmpty())
            throw new BusinessValidationException("service.station.with.name: "+ serviceStationName+ " not.found");

        return serviceStation.get();
    }
}