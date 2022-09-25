package csd.app.product;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;

@Entity
@Getter
@Setter

public class Product {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    @NotNull(message = "Owner ID should not be empty")
    private Integer ownerId;

    @NotNull(message = "Product name should not be empty")
    @Size(min = 1, max = 100, message = "Product name should be at least 1 character long")
    private String name;

    @Size(min = 5, max = 200, message = "Product description should be at least 5 characters long")
    private String description;

    @NotNull(message = "Contact details should not be empty")
    private Integer contactDetails;

    @NotNull(message = "Product name should not be empty")
    @Size(min = 1, max = 100, message = "Product name should be at least 1 character long")
    private String conditions;

    @NotNull(message = "Address should not be empty")
    @Size(min = 5, max = 80, message = "Address should be at least 20 characters")
    private String address;

    @NotNull(message = "Date and time should not be empty")
    private LocalDateTime dateTime;

    public Product() {
    }

    public Product(int ownerId, String name, int contactDetails, String conditions, String address, LocalDateTime dateTime) {
        this.ownerId = ownerId;
        this.name = name;
        this.contactDetails = contactDetails;
        this.conditions = conditions;
        this.address = address;
        this.dateTime = dateTime;
    }
}
