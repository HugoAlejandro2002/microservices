package com.upb.cloudgateway.config;

import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableHystrix
public class GatewayConfig {

    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }
}