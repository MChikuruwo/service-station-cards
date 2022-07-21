package zw.co.jugaad.fudzocommons.security;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zw.co.jugaad.fudzocommons.api.ApiResponse;

@FeignClient(name = "user-service")
public interface AuthServiceFeignClient {

    @GetMapping("/api/v1/users/verify-token")
    ApiResponse verifyToken(@RequestParam ("token") String token);
}