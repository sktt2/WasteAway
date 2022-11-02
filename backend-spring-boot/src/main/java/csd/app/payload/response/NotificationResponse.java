package csd.app.payload.response;

import lombok.*;

@Getter
@Setter
public class NotificationResponse {
    private Long notifid;
    private Long chatid;
    private String takerusername;
    private Long productId;
    private String productName;
    private String productImageUrl;
    private boolean read;
    
    public NotificationResponse(Long notifid, Long chatid, String takerusername, Long productId, String productName,
            String productImageUrl, boolean read) {
        this.notifid = notifid;
        this.chatid = chatid;
        this.takerusername = takerusername;
        this.productId = productId;
        this.productName = productName;
        this.productImageUrl = productImageUrl;
        this.read = read;
    }



}
