package csd.app.product;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private String name;

    @Size(min = 5, max = 200, message = "Product description should be at least 5 characters long")
    private String description;

    @NotNull(message = "Product name should not be empty")
    @Size(min = 1, max = 100, message = "Product name should be at least 1 character long")
    private String conditions;

    @NotNull(message = "Address should not be empty")
    @Size(min = 5, max = 80, message = "Address should be at least 20 characters")
    private String address;

    @NotNull(message = "Date and time should not be empty")
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;


    public Product() {
    }

    public Product(String name, String conditions, String address, LocalDateTime dateTime) {
        this.name = name;
        this.conditions = conditions;
        this.address = address;
        this.dateTime = dateTime;
    }

    public Product(String name, String conditions, String address, LocalDateTime dateTime, String description) {
        this(name, conditions, address, dateTime);
        this.description = description;
    }
}
