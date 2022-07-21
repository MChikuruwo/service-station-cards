package zw.co.jugaad.fudzocommons.dto;

import lombok.Data;
import zw.co.jugaad.fudzocommons.validation.ContactNumberConstraint;

@Data
public class UpdateUserDto {
    private String name;

    private String surname;

    private String userName;
}