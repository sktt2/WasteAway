package csd.app.payload.response;

import lombok.*;

@Getter
@Setter
public class ChatMessageResponse {
    private String content;
    private String dateTime;
    private Long senderId;
    private String senderUsername;
    private Long receiverId;
    private String receiverUsername;
    private Long chatId;

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
