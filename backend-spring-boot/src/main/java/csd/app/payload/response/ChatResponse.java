package csd.app.payload.response;

import lombok.*;

@Getter
@Setter
public class ChatResponse {
    private Long chatId;
    private Long ownerId;
    private String ownerUsername;
    private Long takerId;
    private String takerUsername;
    private Long productId;
    private String productName;
    private String productImageUrl;

    public ChatResponse(Long chatId, Long ownerId, String ownerUsername, Long takerId, String takerUsername,
            Long productId, String productName, String productImageUrl) {
        this.chatId = chatId;
        this.ownerId = ownerId;
        this.ownerUsername = ownerUsername;
        this.takerId = takerId;
        this.takerUsername = takerUsername;
        this.productId = productId;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
    }
}
