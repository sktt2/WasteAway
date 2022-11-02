package csd.app.notification;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import csd.app.chat.Chat;

import lombok.*;

@Entity
@Getter
@Setter
public class Notification {

    @Id @GeneratedValue
    private Long notifid;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "id")
    private Chat chat;
    
    private boolean read;

    public Notification(Chat chat, boolean read) {
        this.chat = chat;
        this.read = read;
    }
    

}
