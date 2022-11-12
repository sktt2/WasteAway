package csd.app.notification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csd.app.user.User;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired NotificationRepository notifications;
    
    public List<Notification> getNotificationbyUsername(String username) {
        List<Notification> allnotifs = notifications.findAll();
        List<Notification> notiflist = new ArrayList<Notification>();
        for (Notification notif : allnotifs) {
            if (notif.getUser().getUsername().equals(username)) {
                notiflist.add(notif);
            }
        }
        return notiflist;
    };

    public Notification getNotificationById(Long id) {
        if (notifications.existsById(id)) {
            return notifications.findById(id)
            .orElseThrow(() -> new RuntimeException("Error: Notification not found."));
        }
        return null;
    };
    
    public Notification addNotification(Notification notif) {
        return notifications.save(notif);
    };

    public Notification updateNotificationIfRead(Notification notif) {
        notif.setRead(true);
        return notifications.save(notif);
    }

    public String getSenderUsername(Notification notif) {
        User taker = notif.getChat().getTaker();
        User owner = notif.getChat().getOwner();
        if (taker.equals(notif.getUser())) {
            return owner.getUsername();
        }
        return taker.getUsername();
    }
}
