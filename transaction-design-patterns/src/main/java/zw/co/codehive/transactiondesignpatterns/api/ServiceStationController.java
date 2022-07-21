package zw.co.codehive.transactiondesignpatterns.api;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.codehive.commons.api.ApiResponse;
import zw.co.codehive.transactiondesignpatterns.dto.response.ServiceStationResponseDto;
import zw.co.codehive.transactiondesignpatterns.dto.serviceStation.ServiceStationRequestDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.ServiceStation;
import zw.co.codehive.transactiondesignpatterns.response.GenericResponse;
import zw.co.codehive.transactiondesignpatterns.service.ServiceStationService;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/service-station")
@Slf4j
public class ServiceStationController {

    private final ServiceStationService serviceStationService;

    public ServiceStationController(ServiceStationService serviceStationService) {
        this.serviceStationService = serviceStationService;
    }

    @PostMapping
    public ApiResponse registerServiceStation(@RequestBody ServiceStationRequestDto requestDto) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
       var serviceStation= serviceStationService.save(requestDto);
        return new ApiResponse(HttpStatus.OK.value(),HttpStatus.OK.toString(),
                new ServiceStationResponseDto(serviceStation.getStationName(),serviceStation.getAddress(),serviceStation.getContactNumber()));
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<ServiceStation>> getServiceStation(@PathVariable("page") int page,
                                                                  @PathVariable("size") int size) {
        return ResponseEntity.ok(serviceStationService.getAllServiceStations(PageRequest.of(page, size)));
    }


    @PutMapping("/edit")
    public ResponseEntity<GenericResponse> editServiceStation(@RequestParam("serviceStationId") Long serviceStationId,
                                                              @RequestBody ServiceStationRequestDto requestDto) throws IllegalAccessException {
        serviceStationService.getOne(serviceStationId);
        serviceStationService.update(requestDto);
        return ResponseEntity.ok(new GenericResponse("Record updated successfully"));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteServiceStation(@PathVariable("id") Long id) {
        serviceStationService.deleteServiceStation(id);
        return ResponseEntity.ok(new GenericResponse("Record deleted successfully"));
    }

    @GetMapping("/name")
    public ApiResponse getServiceStationByName(@RequestParam("serviceStationName") String serviceStationName) {
        var serviceStation =  serviceStationService.findByStationName(serviceStationName);
        return new ApiResponse(HttpStatus.OK.value(),HttpStatus.OK.toString(),
                new ServiceStationResponseDto(serviceStation.getStationName(),serviceStation.getAddress(),serviceStation.getContactNumber()));
    }
}