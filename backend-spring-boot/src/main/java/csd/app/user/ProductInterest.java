package csd.app.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

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

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "id")
    private Product product;

    public ProductInterest(User user, Product product) {
        this.user = user;
        this.product = product;
    }

}
