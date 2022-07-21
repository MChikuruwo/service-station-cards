package zw.co.jugaad.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import zw.co.jugaad.fudzocommons.api.ApiResponse;
import zw.co.jugaad.userservice.dto.CreateRoleDto;
import zw.co.jugaad.userservice.model.Role;
import zw.co.jugaad.userservice.service.iface.RoleService;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping(value = "/api/v1/role", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@RolesAllowed("ADMIN")
public class RoleController {

    private final RoleService roleService;
    private final ModelMapper modelMapper;

    @PostMapping("/create")
    public ApiResponse createRole(@RequestBody CreateRoleDto createRoleDto) {
        var role = modelMapper.map(createRoleDto, Role.class);
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), roleService.add(role));
    }

    @GetMapping("/")
    public ApiResponse getAllRoles() {
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), roleService.getAll());
    }

    @GetMapping("/id")
    public ApiResponse getRoleById(@RequestParam("roleId") Long roleId) {
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), roleService.getOne(roleId));
    }

    @GetMapping("/name")
    public ApiResponse getRoleByName(@RequestParam("roleName") String roleName) {
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name(), roleService.findByName(roleName));
    }

    @DeleteMapping("/delete")
    public ApiResponse deleteRoleById(@RequestParam("roleId") Long roleId) {
        roleService.deleteRole(roleId);
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.name());
    }
}
