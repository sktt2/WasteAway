package csd.app.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;


import lombok.*;

@Entity
@Getter
@Setter
public class ProductInterest {

    @Id @GeneratedValue
    private Long productinterestid;
    
    @ManyToMany
    @JoinTable(name="product_interests",
    joinColumns=@JoinColumn(name="productinterestid"),
    inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private Set<User> users = new HashSet<User>();

    @NotNull
    private @Id Long productid;

    private boolean isfavourited;

    public ProductInterest() {
    }

    public ProductInterest(HashSet<User> users, Long productid, boolean isfavourited) {
        this.users = users;
        this.productid = productid;
        this.isfavourited = isfavourited;
    }

}
