package com.upb.adminservice.service.impl;

import com.upb.adminservice.entity.Notification;
import com.upb.adminservice.model.NotificationRequest;
import com.upb.adminservice.repository.NotificationRepository;
import com.upb.adminservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public Long createNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setAdminId(request.getAdminId());
        notification.setUserId(request.getUserId());
        notification.setTableId(request.getTableId());
        notification.setDate(request.getTimestamp());
        notification.setMessage(request.getMessage());

        notification = notificationRepository.save(notification);
        return notification.getId();
    }
}
