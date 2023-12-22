package com.upb.adminservice.controller;


import com.upb.adminservice.model.NotificationRequest;
import com.upb.adminservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<Long> sendNotification(@RequestBody NotificationRequest notification){
        long notificationId = notificationService.createNotification(notification);
        return new ResponseEntity<>(notificationId, HttpStatus.CREATED);
    }
}
