package zw.co.jugaad.userservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import zw.co.jugaad.fudzocommons.api.ApiResponse;
import zw.co.jugaad.fudzocommons.dto.AddUserDto;
import zw.co.jugaad.fudzocommons.dto.ResetCredentialsDto;
import zw.co.jugaad.fudzocommons.dto.UpdateUserDto;
import zw.co.jugaad.userservice.model.User;
import zw.co.jugaad.userservice.service.iface.RoleService;
import zw.co.jugaad.userservice.service.iface.UserService;

import java.util.Collections;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/signUp")
    @ApiOperation(value = "Sign up a user to the thuli service station Platform.", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse signUpUser(@RequestBody AddUserDto addUserDto,
                                  @RequestParam Long roleId) {
        var user = modelMapper.map(addUserDto, User.class);

        var role = roleService.getOne(roleId);

        // Assign the role of the user
        user.setRole(Collections.singleton(role));

        // Set user active true by default
        user.setActive(true);

        return new ApiResponse(200, "SUCCESS", userService.add(user));
    }

    @PutMapping("/reset-credentials")
    @ApiOperation(value = "Password reset for user.", response = ApiResponse.class)
    public ApiResponse resetCredentialsForUser( @RequestParam ("mobileNumber") String mobileNumber,
            @RequestBody ResetCredentialsDto credentialsDto) {

        // Get user by their mobileNumber
        var user = userService.findByMobileNumber(mobileNumber);

        //set new password by user
        user.setPassword(passwordEncoder.encode(credentialsDto.getPassword()));

        return new ApiResponse(200, "SUCCESS", userService.update(user));
    }

    @GetMapping("/verify-token")
    @ApiOperation(value = "Verify user token", response = ApiResponse.class)
    public ApiResponse verifyToken(@RequestParam("token") String token) {
        return new ApiResponse(200, "SUCCESS", userService.verifyToken(token));
    }

    @GetMapping("/current")
    @ApiOperation(value = "Get currently logged user", response = ApiResponse.class)
    public User getCurrentActualUser() {
        return userService.getCurrentUser();
    }

    @GetMapping("/mobile")
    @ApiOperation(value = "Get currently logged user", response = ApiResponse.class)
    public User getUserByMobileNumber(@RequestParam("number") String number) {
        return userService.findByMobileNumber(number);
    }

    @GetMapping("/user-id")
    @ApiOperation(value = "Get user by userId", response = ApiResponse.class)
    public User getUserByUserId(@RequestParam("Id") String userId) {
        return userService.findByUserId(userId);
    }
}