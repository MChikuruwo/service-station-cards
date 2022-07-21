package zw.co.codehive.transactiondesignpatterns.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import zw.co.codehive.commons.util.OtherUtils;
import zw.co.codehive.transactiondesignpatterns.dto.attendant.AttendantRequestDto;
import zw.co.codehive.transactiondesignpatterns.dto.attendant.AttendantUpdateDto;
import zw.co.codehive.transactiondesignpatterns.dto.serviceStation.TransferRequestDto;
import zw.co.codehive.transactiondesignpatterns.exception.BusinessValidationException;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.Attendant;
import zw.co.codehive.transactiondesignpatterns.model.fudzo.ServiceStation;
import zw.co.codehive.transactiondesignpatterns.repository.fudzo.AttendantRepository;
import zw.co.codehive.transactiondesignpatterns.service.AttendantService;
import zw.co.codehive.transactiondesignpatterns.service.ServiceStationService;
import zw.co.codehive.transactiondesignpatterns.service.UserService;
import zw.co.codehive.transactiondesignpatterns.enums.Attendance;
import zw.co.codehive.transactiondesignpatterns.enums.Role;


@Service
@RequiredArgsConstructor
@Slf4j
public class AttendantServiceImpl implements AttendantService {

    private final AttendantRepository attendantRepository;
    private final ServiceStationService serviceStationService;
    private final UserService userService;

    @Override
    public Attendant save(AttendantRequestDto requestDto) {
        var user = userService.getUserByUserId(requestDto.getUserId());
        extractUser(user.getMobileNumber());
        ServiceStation serviceStation = serviceStationService.findByStationName(requestDto.getServiceStationName());
        Attendant attendant = Attendant.builder()
                .email(user.getEmailAddress())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .attendance(Attendance.ON_DUTY)
                .mobile(user.getMobileNumber())
                .role(Role.ORDINARY)
                .attendantId(OtherUtils.generateAttendantId())
                .serviceStation(serviceStation)
                .userId(user.getUserId())
                .build();
        return attendantRepository.save(attendant);
    }

    @Override
    public void update(AttendantUpdateDto updateDto) {
        ServiceStation serviceStation = serviceStationService.findByStationName(updateDto.getServiceStationName());
        Attendant attendant = Attendant.builder()
                .serviceStation(serviceStation)
                .build();
        attendantRepository.save(attendant);
    }

    @Override
    public void transfer(TransferRequestDto requestDto) {
        ServiceStation serviceStation = serviceStationService.getOne(requestDto.getServiceStationId());
        Attendant attendant = Attendant.builder()
                .serviceStation(serviceStation)
                .build();
        attendantRepository.save(attendant);
    }

    @Override
    public Page<Attendant> getAllAttendants(Pageable pageable) {
        return attendantRepository.findAll(pageable);
    }

    @Override
    public Attendant getAttendant(Long id) {
        return getOne(id);
    }

    @Override
    public void deleteAttendant(Long id) {
        var attendant = getAttendant(id);

        if (attendant == null)
            throw new BusinessValidationException("Attendant with id: " + id + "does not exist");

        attendantRepository.delete(attendant);

    }

    @Override
    public Attendant getOne(Long id) {
        return attendantRepository.findById(id).orElseThrow(
                () -> new BusinessValidationException("Attendant with id " + id + " does not exist")
        );
    }

    @Override
    public Page<Attendant> findAllByServiceStation_Id(Long serviceStationId, Pageable pageable) {
        return attendantRepository.findAllByServiceStation_Id(serviceStationId, pageable);
    }

    @Override
    public Attendant findByAttendantId(String attendantId) {
        var attendant = attendantRepository.findByAttendantId(attendantId);

        if (attendant.isEmpty()) throw new BusinessValidationException("attendant.with.id "+ attendantId + " not.available");

        return attendant.get();
    }

    public boolean extractUser(String mobileNumber) {
        var user = userService.getUserByMobileNumber(mobileNumber);

        return user != null;
    }
}