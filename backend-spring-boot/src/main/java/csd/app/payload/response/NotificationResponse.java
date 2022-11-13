package csd.app.payload.response;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;

import csd.app.notification.Notification;
import lombok.*;

@Getter
public class NotificationResponse {

    @NotNull(message = "Notification Id cannot be null")
    private Long notificationId;

    @NotNull(message = "Chat Id cannot be null")
    private Long chatId;

    @NotBlank(message = "User username cannot be empty or null")
    private String userUsername;

    @NotNull(message = "Product Id cannot be null")
    private Long productId;

    @NotBlank(message = "Product name cannot be empty or null")
    private String productName;

    @NotBlank(message = "Product image URL cannot be empty or null")
    private String productImageUrl;

    @NotBlank(message = "Notification content cannot be empty or null")
    private String notificationContent;

    @NotNull(message = "Notification read state cannot be null")
    private boolean isRead;
    
    @Autowired
    public NotificationResponse(Notification notification) {
        this.notificationId = notification.getId();
        this.chatId = notification.getChat().getId();
        this.userUsername = notification.getUser().getUsername();
        this.productId = notification.getChat().getProduct().getId();
        this.productName = notification.getChat().getProduct().getProductName();
        this.productImageUrl = notification.getChat().getProduct().getImageUrl();
        this.notificationContent = notification.getNotificationContent();
        this.isRead = notification.getIsRead();
    }
}
