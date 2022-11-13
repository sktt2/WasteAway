package csd.app.notification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csd.app.user.User;
import csd.app.user.UserService;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notifications;

    @Autowired
    private UserService userService;
    
    public List<Notification> getNotificationbyUsername(String username) {
        User user = userService.getUserByUsername(username);
        return notifications.findByUser(user);
    }

    public Notification getNotificationById(Long id) {
        return notifications.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Notification not found."));
    }
    
    public Notification addNotification(Notification notif) {
        return notifications.save(notif);
    }

    public Notification updateNotificationIfRead(Notification notif) {
        notif.setIsRead(true);
        return notifications.save(notif);
    }
}
