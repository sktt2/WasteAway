package csd.app.payload.response;

import csd.app.user.User;
import lombok.*;

@Getter
@Setter

public class ProductResponse {
    private Long id;
    private String productName;
    private String condition;
    private String address;
    private String dateTime;
    private String description;
    private int contactDetail;
    private String ownerName;
    private Long ownerId;
    private String category;
    private String imageUrl;

    public ProductResponse(Long id, String productName, String condition, String dateTime,
            String description, String category, String imageUrl, User user) {
        this.id = id;
        this.productName = productName;
        this.condition = condition;
        this.address = user.getUserInfo().getAddress();
        this.dateTime = dateTime;
        this.description = description;
        this.contactDetail = user.getUserInfo().getPhoneNumber();
        this.ownerName = user.getUsername();
        this.ownerId = user.getId();
        this.imageUrl = imageUrl;
        this.category = category;
    }

 
}
