package zw.co.jugaad.transactiondesignpatterns.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zw.co.jugaad.fudzocommons.api.ApiResponse;
import zw.co.jugaad.fudzocommons.dto.*;
import zw.co.jugaad.transactiondesignpatterns.feign.UserWebClient;
import zw.co.jugaad.transactiondesignpatterns.model.user.User;
import zw.co.jugaad.transactiondesignpatterns.repository.user.UserRepository;
import zw.co.jugaad.transactiondesignpatterns.service.UserService;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserWebClient userWebClient;
    private final UserRepository userRepository;

    @Override
    public ApiResponse save(AddUserDto user, Long roleId) {
        return userWebClient.signUpUser(user, roleId);
    }

    @Override
    public ApiResponse updateUser(String emailAddress, UpdateUserDto updateUserDto) {
        return userWebClient.updateUser(emailAddress,updateUserDto);
    }

    @Override
    public User getUserByUserId(String userId) {
        return userWebClient.getUserByUserId(userId);
    }

    @Override
    public User getCurrentActualUser() {
        return userWebClient.getCurrentActualUser();
    }

    @Override
    public User getUserByMobileNumber(String number) {
        return userWebClient.getUserByMobileNumber(number);
    }

    @Override
    public ApiResponse updateAttendant(String mobileNumber, UpdateUserDto updateUserDto) {
        return userWebClient.updateAttendant(mobileNumber, updateUserDto);
    }

    @Override
    public ApiResponse resetCredentialsForUser(ResetCredentialsDto credentialsDto, String emailAddress) {
        return userWebClient.resetCredentialsForUser(credentialsDto, emailAddress);
    }

    @Override
    public ApiResponse resetCredentialsForAttendant(ResetCredentialsDto credentialsDto, String mobileNumber) {
        return userWebClient.resetCredentialsForAttendant(credentialsDto, mobileNumber);
    }

    @Override
    public ResponseEntity loginWithEmailAndPassword(LoginDto accountCredentials) {
        return userWebClient.loginWithEmailAndPassword(accountCredentials);
    }

    @Override
    public ResponseEntity loginWithMobileAndPassword(LoginDto accountCredentials) {
        return userWebClient.loginWithMobileAndPassword(accountCredentials);
    }
}