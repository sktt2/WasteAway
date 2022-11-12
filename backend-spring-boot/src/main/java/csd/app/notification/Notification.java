package csd.app.notification;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import csd.app.chat.Chat;
import csd.app.user.User;
import lombok.*;

@Entity
@Getter
@Setter
public class Notification {

    @Id @GeneratedValue
    private Long notifid;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "notif_user")
    private User user;
    
    private String messageContent;

    private boolean isRead;

    public boolean getIsRead() {
        return isRead;
    }

    public Notification() {

    }
    
    public Notification(Chat chat, User user, boolean isRead) {
        this.chat = chat;
        this.isRead = isRead;
        this.user = user;
    }
    

}
