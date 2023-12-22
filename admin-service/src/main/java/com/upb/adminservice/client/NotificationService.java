package com.upb.adminservice.client;


import com.upb.adminservice.model.NotificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "NOTIFICATION-SERVICE/notifications")
public interface NotificationService {
    @PostMapping
    public ResponseEntity<Long> sendNotification(@RequestBody NotificationRequest notification);
}
