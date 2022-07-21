package zw.co.codehive.commons.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.codehive.commons.enums.Organisation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String firstName;
    private String lastName;
    private String userName;
    private Organisation organisation;
}