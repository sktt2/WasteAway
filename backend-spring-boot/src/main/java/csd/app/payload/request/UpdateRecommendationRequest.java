package csd.app.payload.request;

import javax.validation.constraints.*;

import lombok.ToString;

@ToString
public class UpdateRecommendationRequest {
    @NotNull (message = "Recommendation should not be empty")
    private String recommendation;

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
