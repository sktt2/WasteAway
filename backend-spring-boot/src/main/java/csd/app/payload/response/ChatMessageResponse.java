package csd.app.payload.response;

import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

public class ChatMessageResponse {

    @NotBlank(message = "Message conte cannot be empty or null")
    private String content;

    @NotBlank(message = "Date and time cannot be empty or null")
    private String dateTime;

    @NotBlank(message = "Sender Id cannot be empty or null")
    private Long senderId;

    @NotBlank(message = "Sender username cannot be empty or null")
    private String senderUsername;

    @NotBlank(message = "Receiver Id cannot be empty or null")
    private Long receiverId;

    @NotBlank(message = "Receiver username cannot be empty or null")
    private String receiverUsername;

    @NotBlank(message = "Chat Id cannot be empty or null")
    private Long chatId;

    @Autowired
    public ChatMessageResponse(String content, String dateTime, Long senderId, String senderUsername, Long receiverId,
            String receiverUsername, Long chatId) {
        this.content = content;
        this.dateTime = dateTime;
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.receiverId = receiverId;
        this.receiverUsername = receiverUsername;
        this.chatId = chatId;
    }
}
