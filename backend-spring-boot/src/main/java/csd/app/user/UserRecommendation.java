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
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import csd.app.product.Product;
import lombok.*;

@Entity
@Getter
@Setter
public class UserRecommendation {
    
    @Id @GeneratedValue
    private Long recommendationId;

    @NotNull(message="recommendation should not be null")
    private String recommendation;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Product> products = new HashSet<>();

    public UserRecommendation() {

    }

    public UserRecommendation(String recommendation, User user) {
        this.recommendation = recommendation;
        this.user = user;
    }

    public UserRecommendation(String recommendation, User user, Set<Product> products) {
        this.recommendation = recommendation;
        this.user = user;
        this.products = products;
    }
}
