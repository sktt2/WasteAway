package csd.app.payload.request;

import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationRequest {
    @NotBlank(message = "Recommendation cannot be empty")
    private String recommendation;

    @NotBlank(message = "Username cannot be empty")
    private String username;
 
    public RecommendationRequest() {

    }

    public RecommendationRequest(String recommendation, String username) {
        this.recommendation = recommendation;
        this.username = username;
    }
}