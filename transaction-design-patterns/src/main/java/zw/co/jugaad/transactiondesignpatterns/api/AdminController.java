package zw.co.jugaad.transactiondesignpatterns.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import zw.co.jugaad.fudzocommons.api.ApiResponse;
import zw.co.jugaad.transactiondesignpatterns.dto.admin.AdminCreateDto;
import zw.co.jugaad.transactiondesignpatterns.service.AdminService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/admin")
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/add")
    public ApiResponse addAdmin(@RequestBody AdminCreateDto adminCreateDto){
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(), adminService.add(adminCreateDto));
    }

    @GetMapping("/user-name")
    public ApiResponse getByUserName(@RequestParam("userName") String userName){
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(), adminService.findByUserName(userName));

    }

    @GetMapping("/{id}")
    public ApiResponse getById(@PathVariable("id") Long id){
        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString(), adminService.getOne(id));

    }

    @DeleteMapping("delete/{id}")
    public ApiResponse deleteUser(@PathVariable("id") Long id){

        adminService.delete(id);

        return new ApiResponse(HttpStatus.OK.value(), HttpStatus.OK.toString());

    }
}
