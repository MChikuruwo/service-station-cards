package zw.co.jugaad.fudzocommons.dto;

import lombok.Data;
import zw.co.jugaad.fudzocommons.enums.Organisation;
import zw.co.jugaad.fudzocommons.validation.ContactNumberConstraint;

import javax.validation.constraints.Email;


@Data
public class AddUserDto {
    private String firstName;
    private String lastName;
    private Organisation organisation;
    @Email
    private String emailAddress;
    private String userName;
    @ContactNumberConstraint
    private String mobileNumber;
    private String password;

}
