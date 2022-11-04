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
    
    @Id @GeneratedValue
    private Long recommendationId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Product> product = new HashSet<>();

    private UserRecommendation(){

    }

    private UserRecommendation(User user, Set<Product> product) {
        this.user = user;
        this.product = product;
    }
}
