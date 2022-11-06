package csd.app.user;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import csd.app.product.Product;
import lombok.*;

@Entity
@Getter
@Setter
public class UserRecommendation {
    
    @Id 
    private Long user_Id;

    private String recommendation;

    public UserRecommendation() {

    }

    public UserRecommendation(String recommendation, Long userId) {
        this.recommendation = recommendation;
        this.user_Id = userId;
    }

}
