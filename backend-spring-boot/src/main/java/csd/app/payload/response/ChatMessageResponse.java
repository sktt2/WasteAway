package csd.app.payload.response;

import csd.app.user.*;
import csd.app.chat.*;
import javax.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.*;

@Getter
public class ChatMessageResponse {

    @NotBlank(message = "Message content cannot be empty or null")
    @Size(max = 2000, message = "Message content must be 2000 characters or lower.")
    private String content;

    @NotBlank(message = "Date and time cannot be empty or null")
    private String dateTime;

    @NotNull(message = "Sender Id cannot be null")
    private Long senderId;

    @NotBlank(message = "Sender username cannot be empty or null")
    private String senderUsername;

    @NotNull(message = "Receiver Id cannot be null")
    private Long receiverId;

    @NotBlank(message = "Receiver username cannot be empty or null")
    private String receiverUsername;

    @NotNull(message = "Chat Id cannot be null")
    private Long chatId;

    @Autowired
    public ChatMessageResponse(Message message, User sender, User receiver, Long chatId) {
        this.content = message.getContent();
        this.dateTime = message.getDateTime();
        this.senderId = sender.getId();
        this.senderUsername = sender.getUsername();
        this.receiverId = receiver.getId();
        this.receiverUsername = receiver.getUsername();
        this.chatId = chatId;
    }
}
