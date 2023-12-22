package com.upb.authservice.service;

import com.upb.authservice.model.NotificationRequest;

public interface NotificationService {
    Long createNotification(NotificationRequest request);
}
