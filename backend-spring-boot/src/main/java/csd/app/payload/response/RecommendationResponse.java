package csd.app.payload.response;

import lombok.*;

@Getter
@Setter
public class RecommendationResponse {
    private Long id;
    private Long userId;
    private Long productId;

    public RecommendationResponse(Long id, Long userId, Long productId) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
    }
}