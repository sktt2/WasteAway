package csd.app.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.*;

@Entity
@Getter
@Setter
public class UserRecommendation {
    
    @Id @NotNull(message = "User Id cannot be empty")
    private Long userId;

    @NotNull (message = "Recommendation cannot be empty")
    private String recommendation;

    public UserRecommendation() {

    }

    public UserRecommendation(String recommendation, Long userId) {
        this.recommendation = recommendation;
        this.userId = userId;
    }

}
