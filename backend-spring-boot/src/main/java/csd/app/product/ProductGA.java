package csd.app.product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@Entity
@Getter
@Setter

public class ProductGA {

    @NotNull
    private @Id Long id;

    @Nullable
    private Long receiverId;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "id")
    private Product product;

    public ProductGA() {
    }

    public ProductGA(Long productId) {
        this.id = productId;
    }

    public ProductGA(Long productId, Long receiverId) {
        this(productId);
        this.receiverId = receiverId;
    }
}
