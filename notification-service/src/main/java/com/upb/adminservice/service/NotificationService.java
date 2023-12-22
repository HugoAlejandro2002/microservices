package com.upb.adminservice.service;

import com.upb.adminservice.model.NotificationRequest;

public interface NotificationService {
    Long createNotification(NotificationRequest request);
}
