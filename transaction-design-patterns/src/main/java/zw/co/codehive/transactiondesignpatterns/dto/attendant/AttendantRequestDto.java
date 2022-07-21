package zw.co.codehive.transactiondesignpatterns.dto.attendant;


import lombok.Data;

@Data
public class AttendantRequestDto {
    private String serviceStationName;
    private String userId;
}