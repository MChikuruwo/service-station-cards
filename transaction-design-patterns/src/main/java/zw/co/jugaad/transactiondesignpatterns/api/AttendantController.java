package zw.co.jugaad.transactiondesignpatterns.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zw.co.jugaad.fudzocommons.api.ApiResponse;
import zw.co.jugaad.transactiondesignpatterns.dto.attendant.AttendantRequestDto;
import zw.co.jugaad.transactiondesignpatterns.dto.attendant.AttendantUpdateDto;
import zw.co.jugaad.transactiondesignpatterns.dto.response.AttendantResponseDto;
import zw.co.jugaad.transactiondesignpatterns.dto.response.ServiceStationResponseDto;
import zw.co.jugaad.transactiondesignpatterns.dto.serviceStation.TransferRequestDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Attendant;
import zw.co.jugaad.transactiondesignpatterns.response.GenericResponse;
import zw.co.jugaad.transactiondesignpatterns.service.AttendantService;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/attendant")
@Slf4j
public class AttendantController {

    private final AttendantService attendantService;

    public AttendantController(AttendantService attendantService) {
        this.attendantService = attendantService;
    }

    @PostMapping
    public ApiResponse registerAttendant(@RequestBody AttendantRequestDto requestDto) throws IllegalAccessException, JsonProcessingException {
      var attendant=attendantService.save(requestDto);
        return new ApiResponse(HttpStatus.OK.value(),HttpStatus.OK.toString(),
                new AttendantResponseDto(attendant.getAttendantId(), attendant.getFirstName(), attendant.getLastName(),
                        attendant.getAttendance().name(),attendant.getServiceStation().getStationName()));
    }

    @GetMapping("/attendant-id")
    public Attendant getAttendantByAttendantId(@RequestParam("attendantId") String attendantId) {
        return attendantService.findByAttendantId(attendantId);
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<Attendant>> getAttendants(@PathVariable("page") int page,
                                                         @PathVariable("size") int size) {
        return ResponseEntity.ok(attendantService.getAllAttendants(PageRequest.of(page, size)));
    }


    @PutMapping("/edit")
    public ResponseEntity<GenericResponse> editAttendant(@RequestParam("attendant-id") String attendantId,
                                                         @RequestBody AttendantUpdateDto updateDto) throws IllegalAccessException {
        attendantService.findByAttendantId(attendantId);
        attendantService.update(updateDto);
        return ResponseEntity.ok(new GenericResponse("Record updated successfully"));
    }

    @PutMapping("/transfer")
    public ResponseEntity<GenericResponse> transferAttendant(@RequestParam("attendantId") String attendantId,
                                                             @RequestBody TransferRequestDto requestDto) throws IllegalAccessException {
        attendantService.findByAttendantId(attendantId);
        attendantService.transfer(requestDto);
        return ResponseEntity.ok(new GenericResponse("Record updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse> deleteAttendant(@PathVariable("id") Long id) {
        attendantService.deleteAttendant(id);
        return ResponseEntity.ok(new GenericResponse("Record deleted successfully"));
    }


    @GetMapping("/{service-station-id}/{page}/{size}")
    public ResponseEntity<Page<Attendant>> getAttendantsByServiceStationId(@PathVariable("service-station-id") Long serviceStationId,
                                                                           @PathVariable("page") int page,
                                                                           @PathVariable("size") int size) {

        return ResponseEntity.ok(attendantService.findAllByServiceStation_Id(serviceStationId, PageRequest.of(page, size)));
    }
}
