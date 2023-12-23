package com.upb.cloudgateway.filters;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.beans.factory.annotation.Value;


import java.net.URI;

@Component
@Log4j2
public class AdminAuthenticationFilter extends AbstractGatewayFilterFactory<AdminAuthenticationFilter.Config> {

    @Autowired
    private RouterValidator validator;

    @Autowired
    private RestTemplate template;
    
    @Value("${auth.service.url}")
    private String authServiceUrl;

    public AdminAuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (validator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    throw new RuntimeException("missing authorization header");
                }

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }
                try {
                    String response = template.getForObject(authServiceUrl + "/validate?token=" + authHeader, String.class);                    log.info("Response from auth service: " + response);
                    JSONObject obj = new JSONObject(response);
                    String role = obj.getString("role");
                    if (!"ROLE_ADMIN".equals(role)) {
                        return onError(exchange, HttpStatus.FORBIDDEN);
                    }
                    String userId = obj.getString("id");
                    String url = exchange.transformUrl(exchange.getRequest().getURI() + "?userId=" + userId);
                    URI uri = exchange.getRequest().getURI().resolve(url);
                    ServerHttpRequest request = exchange.getRequest().mutate().uri(uri).build();
                    exchange = exchange.mutate().request(request).build();
                    log.info("response from auth service: " + response);
                } catch (Exception e) {
                    log.info("invalid token");
                    log.error(e.getMessage());
                    return onError(exchange, HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        return request.getHeaders().getOrEmpty("Authorization").get(0);
    }


    public static class Config {
    }
}