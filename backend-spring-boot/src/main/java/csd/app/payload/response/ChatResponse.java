package csd.app.payload.response;

import javax.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;

public class ChatResponse {

    @NotBlank(message="Chat Id cannot be empty or null")
    private Long chatId;

    @NotBlank(message="Owner Id cannot be empty or null")
    private Long ownerId;

    @NotBlank(message="Owner username cannot be empty or null")
    private String ownerUsername;

    @NotBlank(message="Taker Id cannot be empty or null")
    private Long takerId;

    @NotBlank(message="Taker username cannot be empty or null")
    private String takerUsername;

    @NotBlank(message="Product Id cannot be empty or null")
    private Long productId;

    @NotBlank(message="Product name cannot be empty or null")
    private String productName;

    @NotBlank(message="Product image URL cannot be empty or null")
    private String productImageUrl;

    @Autowired
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
