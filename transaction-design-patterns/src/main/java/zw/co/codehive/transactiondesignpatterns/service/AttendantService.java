package zw.co.codehive.transactiondesignpatterns.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import zw.co.codehive.transactiondesignpatterns.dto.attendant.AttendantRequestDto;
import zw.co.codehive.transactiondesignpatterns.dto.attendant.AttendantUpdateDto;
import zw.co.codehive.transactiondesignpatterns.dto.serviceStation.TransferRequestDto;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Attendant;

public interface AttendantService {

    Attendant save(AttendantRequestDto requestDto);

    void update(AttendantUpdateDto updateDto);

    void transfer(TransferRequestDto requestDto);

    Page<Attendant> getAllAttendants(Pageable pageable);

    Attendant getAttendant(Long id);

    void deleteAttendant(Long id);

    Attendant getOne(Long id);

    Page<Attendant> findAllByServiceStation_Id(Long serviceStationId, Pageable pageable);

   Attendant findByAttendantId(String attendantId);
}