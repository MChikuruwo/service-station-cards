package zw.co.codehive.userservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zw.co.codehive.commons.api.ApiResponse;
import zw.co.codehive.commons.dto.LoginDto;
import zw.co.codehive.commons.response.LoginResponse;
import zw.co.codehive.commons.security.JwtTokenProvider;
import zw.co.codehive.userservice.service.iface.LoginService;
import zw.co.codehive.userservice.service.iface.UserService;
import zw.co.codehive.userservice.model.Login;

@RestController
@RequestMapping(value = "/api/v1/secure/login", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/secure", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class LoginController {

    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    private final UserService userService;
    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    ApiResponse response;

    @PostMapping(value = "/")
    @ApiOperation("Enables a user to login with mobile number & password")
    public ResponseEntity loginWithMobileAndPassword(@RequestBody LoginDto accountCredentials) {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(
                        accountCredentials.getMobileNumber(),
                        accountCredentials.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        //Check if the authentication was successful. If it is, then return the details of the user

        if (authentication.isAuthenticated()) {
            var authenticatedUser = userService.findByMobileNumber(accountCredentials.getMobileNumber());

            // Log user login in database
            var login = new Login();
            login.setUser(authenticatedUser);
            loginService.add(login);

            response = new ApiResponse(200, "SUCCESS", new LoginResponse(authenticatedUser.getFirstName(),
                    authenticatedUser.getLastName(), authenticatedUser.getUserName(),authenticatedUser.getOrganisation()));
            return ResponseEntity.ok().header(HEADER_STRING, TOKEN_PREFIX + " " + jwt).body(response);
        } else {
            response = new ApiResponse(400, "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).contentType(MediaType.TEXT_PLAIN).body(response);
        }
    }
}