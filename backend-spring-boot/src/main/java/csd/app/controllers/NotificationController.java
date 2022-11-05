package csd.app.controllers;

import java.util.*;

import csd.app.notification.Notification;
import csd.app.notification.NotificationService;
import csd.app.payload.response.NotificationResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/api/notifications/{username}")
    public List<NotificationResponse> getNotificationByUsername(@PathVariable String username) {
        List<Notification> notiflist = notificationService.getNotificationbyUsername(username);
        List<NotificationResponse> resp = new ArrayList<NotificationResponse>();
        for (Notification notif : notiflist) {
            NotificationResponse notifasresponse =
            new NotificationResponse(notif.getNotifid(), notif.getChat().getId(), notif.getChat().getTaker().getUsername(), notif.getChat().getProduct().getId(), notif.getChat().getProduct().getProductName(), notif.getChat().getProduct().getImageUrl(), notif.getMessageContent(), false);
            resp.add(notifasresponse);
        }
        return resp;
    }
}
