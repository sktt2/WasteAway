package csd.app.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import csd.app.product.Product;
import lombok.*;

@Entity
@Getter
@Setter
public class Recommendation {
    
    @Id @GeneratedValue
    private Long recommendationId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy="product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Product product;

    private Recommendation(){

    }

    private Recommendation(User user, Product product) {
        this.user = user;
        this.product = product;
    } 
}
