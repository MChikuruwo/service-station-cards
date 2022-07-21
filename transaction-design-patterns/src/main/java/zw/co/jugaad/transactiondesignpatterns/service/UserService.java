package zw.co.jugaad.transactiondesignpatterns.service;

import org.springframework.http.ResponseEntity;
import zw.co.jugaad.fudzocommons.api.ApiResponse;
import zw.co.jugaad.fudzocommons.dto.*;
import zw.co.jugaad.transactiondesignpatterns.model.user.User;

public interface UserService {
    ApiResponse save(AddUserDto user, Long roleId);

    ApiResponse updateUser(String emailAddress, UpdateUserDto updateUserDto);

    User getUserByUserId(String userId);

    User getCurrentActualUser();

    User getUserByMobileNumber(String number);


    ApiResponse updateAttendant(String mobileNumber, UpdateUserDto updateUserDto);

    ApiResponse resetCredentialsForUser(ResetCredentialsDto credentialsDto, String emailAddress);

    ApiResponse resetCredentialsForAttendant(ResetCredentialsDto credentialsDto, String mobileNumber);

    ResponseEntity loginWithEmailAndPassword(LoginDto accountCredentials);

    ResponseEntity loginWithMobileAndPassword(LoginDto accountCredentials);
}