package com.upb.adminservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class NotificationRequest {
    private Long adminId;
    private Long userId;
    private Long tableId;
    private Date timestamp;
    private String message;
}