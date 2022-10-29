package csd.app.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationRequest {
    private String username;
    private String productId;

    public String getUsername() {
        return username;
    }

    public String getProductId() {
        return productId;
    }

    public RecommendationRequest() {

    }

    public RecommendationRequest(String username, String productId) {
        this.username = username;
        this.productId = productId;
    }
}