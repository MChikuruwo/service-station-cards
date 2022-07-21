package zw.co.codehive.transactiondesignpatterns.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zw.co.codehive.commons.api.ApiResponse;
import zw.co.codehive.commons.dto.AddUserDto;
import zw.co.codehive.commons.dto.LoginDto;
import zw.co.codehive.commons.dto.ResetCredentialsDto;
import zw.co.codehive.commons.dto.UpdateUserDto;
import zw.co.codehive.transactiondesignpatterns.model.user.User;

@FeignClient(value = "USER-SERVICE",decode404 = true)
public interface UserWebClient {

    @PostMapping(value = "/api/v1/users/signUp")
    ApiResponse signUpUser(@RequestBody AddUserDto addUserDto,
                           @RequestParam Long roleId);

    @PutMapping(value = "/api/v1/users/update")
    ApiResponse updateUser(@RequestParam String emailAddress,
                           @RequestBody UpdateUserDto updateUserDto);

    @PutMapping(value = "/api/v1/users/update/attendant")
    ApiResponse updateAttendant(@RequestParam String mobileNumber,
                                @RequestBody UpdateUserDto updateUserDto);

    @PutMapping("/api/v1/users/reset-credentials")
    ApiResponse resetCredentialsForUser(@RequestBody ResetCredentialsDto credentialsDto,
                                        @RequestParam String emailAddress);

    @PutMapping("/api/v1/users/reset-credentials/attendant")
    ApiResponse resetCredentialsForAttendant(@RequestBody ResetCredentialsDto credentialsDto,
                                             @RequestParam String mobileNumber);

    @PostMapping(value = "/api/v1/secure/login")
    ResponseEntity loginWithEmailAndPassword(@RequestBody LoginDto accountCredentials);

    @PostMapping(value = "/api/v1/secure/login/attendant")
    ResponseEntity loginWithMobileAndPassword(@RequestBody LoginDto accountCredentials);

    @GetMapping("/api/v1/users/user-id")
    User getUserByUserId(@RequestParam("Id") String userId);

    @GetMapping("/api/v1/users/current")
    User getCurrentActualUser();

    @GetMapping("/api/v1/users/mobile")
    User getUserByMobileNumber(@RequestParam("number") String number);
}