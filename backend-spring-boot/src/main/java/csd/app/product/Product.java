package csd.app.product;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import csd.app.user.User;
import lombok.*;

@Entity
@Getter
@Setter

public class Product {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @NotNull(message = "Product name should not be empty")
    @Size(min = 1, max = 100, message = "Product name should be at least 1 character long")
    private String productName;

    @Size(min = 5, max = 200, message = "Product description should be at least 5 characters long")
    private String description;

    @NotNull(message = "Product name should not be empty")
    @Size(min = 1, max = 100, message = "Product name should be at least 1 character long")
    @Column(name="conditions")
    private String condition;

    @NotNull(message = "Date and time should not be empty")
    private String dateTime;

    @NotNull(message = "Category should not be empty")
    private String category;

    // To be done
    // @NotNull(message = "Image url should not be empty")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="owner_id", nullable=false)
    private User user;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductGA productGA;

    public Product() {
    }

    public Product(String productName, String condition, String dateTime, String category) {
        this.productName = productName;
        this.condition = condition;
        this.dateTime = dateTime;
        this.category = category;
    }

    public Product(String productName, String condition, String dateTime, String category, String description) {
        this(productName, condition, dateTime, category);
        this.description = description;
    }
}
