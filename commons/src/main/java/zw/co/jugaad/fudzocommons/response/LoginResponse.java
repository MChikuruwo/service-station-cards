package zw.co.jugaad.fudzocommons.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import zw.co.jugaad.fudzocommons.enums.Organisation;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String firstName;
    private String lastName;
    private String userName;
    private Organisation organisation;
}