package zw.co.jugaad.transactiondesignpatterns.dto.attendant;


import lombok.Data;
import zw.co.jugaad.transactiondesignpatterns.enums.Role;

@Data
public class AttendantRequestDto {
    private String serviceStationName;
    private String userId;
}