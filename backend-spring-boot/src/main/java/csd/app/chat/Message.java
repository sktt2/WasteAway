package csd.app.chat;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import csd.app.user.*;

@Entity
@Getter
@Setter
public class Message {
    
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @NotNull(message = "Message cannot be empty")
    @Size(min = 1, message = "Message cannot be empty")
    private String content;

    @NotNull(message = "Date and time should not be empty")
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

    public Message() {
    }

    public Message(String content, String dateTime) {
        this.content = content;
        this.dateTime = dateTime;
    }
}
