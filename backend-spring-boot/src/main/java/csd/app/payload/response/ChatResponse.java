package csd.app.payload.response;

import csd.app.user.*;
import csd.app.product.*;
import javax.validation.constraints.*;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.*;

@Getter
public class ChatResponse {

    @NotNull(message="Chat Id cannot be null")
    private Long chatId;

    @NotNull(message="Owner Id cannot be null")
    private Long ownerId;

    @NotBlank(message="Owner username cannot be empty or null")
    private String ownerUsername;

    @NotNull(message="Taker Id cannot be null")
    private Long takerId;

    @NotBlank(message="Taker username cannot be empty or null")
    private String takerUsername;

    @NotNull(message="Product Id cannot be null")
    private Long productId;

    @NotBlank(message="Product name cannot be empty or null")
    private String productName;

    @NotBlank(message="Product image URL cannot be empty or null")
    private String productImageUrl;

    @Autowired
    public ChatResponse(Long chatId, User owner, User taker, Product product) {
        this.chatId = chatId;
        this.ownerId = owner.getId();
        this.ownerUsername = owner.getUsername();
        this.takerId = taker.getId();
        this.takerUsername = taker.getUsername();
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.productImageUrl = product.getImageUrl();
    }
}
