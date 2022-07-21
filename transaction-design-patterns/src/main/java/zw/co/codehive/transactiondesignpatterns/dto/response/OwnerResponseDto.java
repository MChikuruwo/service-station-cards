package zw.co.codehive.transactiondesignpatterns.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.codehive.commons.enums.Organisation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerResponseDto {
    private String ownerId;
    private String firstName;
    private String lastName;
    private String mobile;
    private String companyName;
    private Organisation organisation;
}