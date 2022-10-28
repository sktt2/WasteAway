package csd.app.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import csd.app.product.Product;
import lombok.*;

@Entity
@Getter
@Setter
public class ProductInterest {

    @Id @GeneratedValue
    private Long productinterestid;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public ProductInterest() {
    }

    @ManyToOne
    @JoinColumn(name = "id")
    private Product product;

    public ProductInterest(User user, Product product) {
        this.user = user;
        this.product = product;
    }

}
