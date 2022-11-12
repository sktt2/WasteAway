package csd.app.notification;

import java.util.List;

public interface NotificationService {
    List<Notification> getNotificationbyUsername(String username);
    Notification getNotificationById(Long id);
    Notification addNotification(Notification notif);
    Notification updateNotificationIfRead(Notification notif);
    String getSenderUsername(Notification notif);
}
