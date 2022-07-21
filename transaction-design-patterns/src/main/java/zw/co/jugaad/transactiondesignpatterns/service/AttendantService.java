package zw.co.jugaad.transactiondesignpatterns.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import zw.co.jugaad.transactiondesignpatterns.dto.attendant.AttendantRequestDto;
import zw.co.jugaad.transactiondesignpatterns.dto.attendant.AttendantUpdateDto;
import zw.co.jugaad.transactiondesignpatterns.dto.serviceStation.TransferRequestDto;
import zw.co.jugaad.transactiondesignpatterns.model.fudzo.Attendant;

import java.util.Optional;

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