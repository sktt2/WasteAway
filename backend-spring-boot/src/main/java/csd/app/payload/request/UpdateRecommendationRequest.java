package csd.app.payload.request;

import javax.validation.constraints.*;

import lombok.ToString;

@ToString
public class UpdateRecommendationRequest {
    @NotNull
    private String recommendation;

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
}
