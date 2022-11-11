package csd.app.chat;

import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import csd.app.user.*;

@Entity
@Getter
public class Message {
    
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @NotBlank(message = "Message content cannot be empty or null")
    @Size(max = 2000, message = "Message content must be 2000 characters or lower.")
    private String content;

    @NotBlank(message = "Date and time cannot be empty or null")
    private String dateTime;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "chat_id", nullable = false)
    private Chat chat;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Autowired
    public Message(String content, String dateTime, Chat chat, User sender, User receiver) {
        this.content = content;
        this.dateTime = dateTime;
        this.chat = chat;
        this.sender = sender;
        this.receiver = receiver;
    }

    public Message() {
    }

    public void setId(Long id) {
        this.id = id;
    }
}
