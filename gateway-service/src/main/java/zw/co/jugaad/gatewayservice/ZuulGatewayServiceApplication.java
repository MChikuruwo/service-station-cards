package zw.co.jugaad.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import zw.co.jugaad.gatewayservice.filters.ErrorFilter;
import zw.co.jugaad.gatewayservice.filters.PostFilter;
import zw.co.jugaad.gatewayservice.filters.PreFilter;
import zw.co.jugaad.gatewayservice.filters.RouteFilter;

@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class ZuulGatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayServiceApplication.class, args);
    }


    @Bean
    public PreFilter preFilter() {
        return new PreFilter();
    }

    @Bean
    public PostFilter postFilter() {
        return new PostFilter();
    }

    @Bean
    public ErrorFilter errorFilter() {
        return new ErrorFilter();
    }

    @Bean
    public RouteFilter routeFilter() {
        return new RouteFilter();
    }

}