package csd.app.payload.response;

import lombok.*;

@Getter
@Setter

public class ProductInterestResponse {

    private Long id;
    private Long ownerId;
    private Long interestedUserId;
    private Long productId;
    private String interestedUsername;

    public ProductInterestResponse(Long id,  Long productId, Long ownerId, Long interestedUserId, String interestedUsername) {
        this.id = id;
        this.ownerId = ownerId;
        this.productId = productId;
        this.interestedUserId =  interestedUserId;
        this.interestedUsername = interestedUsername;
    }


}
