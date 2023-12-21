package com.upb.authservice.controller;


import com.upb.authservice.model.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    @PostMapping
    public ResponseEntity<Long> sendNotification(@RequestBody NotificationRequest notification){
        long notificationId = notificationService.addTable(notification);
        return new ResponseEntity<>(notificationId, HttpStatus.CREATED);
    }
}
