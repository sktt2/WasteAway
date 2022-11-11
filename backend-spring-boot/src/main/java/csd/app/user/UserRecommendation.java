package csd.app.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

import lombok.*;

@Entity
@Getter
@Setter
public class UserRecommendation {
    
    @Id @NotNull(message = "User Id cannot be empty")
    private Long userId;

    @NotBlank (message = "Recommendation cannot be empty")
    private String recommendation;

    public UserRecommendation() {

    }

    public UserRecommendation(String recommendation, Long userId) {
        this.recommendation = recommendation;
        this.userId = userId;
    }

}
