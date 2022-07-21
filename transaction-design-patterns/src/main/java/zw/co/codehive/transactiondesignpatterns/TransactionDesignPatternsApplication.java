package zw.co.codehive.transactiondesignpatterns;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import zw.co.codehive.commons.config.FeignClientSecurityConfig;
import zw.co.codehive.commons.config.LocalRibbonClientConfiguration;
import zw.co.codehive.commons.security.JwtAuthenticationEntryPoint;

@SpringBootApplication(scanBasePackages = "zw.co.jugaad")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"zw.co.jugaad"})
@Import({FeignClientSecurityConfig.class})
@RibbonClient(name = "FUDZO-MAIN-SERVICE", configuration = LocalRibbonClientConfiguration.class)
public class TransactionDesignPatternsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionDesignPatternsApplication.class, args);
    }

    @Bean
    public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}