package zw.co.jugaad.userservice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import zw.co.jugaad.fudzocommons.api.ApiResponse;
import zw.co.jugaad.userservice.model.User;
import zw.co.jugaad.userservice.service.iface.UserService;

@RestController
@RequestMapping(value = "/api/v1/users/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/api/v1/users/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/")
    @ApiOperation(value = "Get all users", response = ApiResponse.class)
    public ApiResponse getAllUsers() {
        return new ApiResponse(200, "SUCCESS", userService.getAll());
    }

    @GetMapping("/id")
    @ApiOperation(value = "Get one user by their userId", response = ApiResponse.class)
    public User getOneUser(@RequestParam("userId") String userId) {
        return userService.findByUserId(userId);
    }
}