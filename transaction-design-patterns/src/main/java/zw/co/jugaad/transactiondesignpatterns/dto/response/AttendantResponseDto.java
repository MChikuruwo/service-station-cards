package zw.co.jugaad.transactiondesignpatterns.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendantResponseDto {
    private String attendantId;
    private String firstName;
    private String lastName;
    private String attendance;
    private String serviceStationId;
}