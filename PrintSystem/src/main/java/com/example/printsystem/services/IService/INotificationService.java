package com.example.printsystem.services.IService;

import com.example.printsystem.models.entity.Notification;

public interface INotificationService {
    Notification createNotify(Long receiverId, String message, String link);
    Notification seenNotify(Long notifyId) throws Exception;
}
