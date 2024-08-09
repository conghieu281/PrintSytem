package com.example.printsystem.services;

import com.example.printsystem.models.entity.Notification;
import com.example.printsystem.models.repository.NotificationRepository;
import com.example.printsystem.services.IService.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificationService implements INotificationService {
    @Autowired
    private NotificationRepository _notificationRepository;

    @Override
    public Notification createNotify(Long receiverId, String message, String link) {
        Notification notification = new Notification();
        notification.setUserId(receiverId);
        notification.setContent(message);
        notification.setLink(link);
        notification.setCreateTime(LocalDateTime.now());
        notification.setIsSeen(false);
        return _notificationRepository.save(notification);
    }

    @Override
    public Notification seenNotify(Long notifyId) throws Exception {
        Notification notification = _notificationRepository.findById(notifyId).orElseThrow(() -> new Exception("Notification not found"));
        notification.setIsSeen(true);
        return _notificationRepository.save(notification);
    }
}
