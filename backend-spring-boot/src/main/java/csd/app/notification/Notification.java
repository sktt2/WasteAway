package csd.app.notification;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType; 
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonBackReference;

import csd.app.chat.Chat;
import csd.app.user.User;
import lombok.*;

@Entity
@Getter
public class Notification {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;
    
    @NotBlank(message = "Notification content cannot be empty or null")
    private String notificationContent;

    @NotNull(message = "Notification read state cannot be null")
    private Boolean isRead;

    @Autowired
    public Notification(Chat chat, User user, String notificationContent) {
        this.chat = chat;
        this.isRead = false;
        this.user = user;
        this.notificationContent = notificationContent;
    }
    
    public Notification() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
