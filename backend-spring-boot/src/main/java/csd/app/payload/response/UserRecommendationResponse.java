package csd.app.payload.response;

import lombok.*;

@Getter
@Setter
public class UserRecommendationResponse {
    private Long id;
    private Long userId;

    public UserRecommendationResponse(Long id, Long userId, Long productId) {
        this.id = id;
        this.userId = userId;
    }
}