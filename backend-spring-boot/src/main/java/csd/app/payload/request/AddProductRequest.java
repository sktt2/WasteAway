package csd.app.payload.request;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter

public class AddProductRequest {
    
    @NotNull(message = "Product name should not be empty")
    @Size(min = 1, max = 100, message = "Product name should be at least 1 character long")
    private String productName;

    @NotNull(message = "Category should not be empty")
    private String category;

    @NotNull(message = "Product description should not be empty")
    @Size(min = 5, max = 200, message = "Product description should be at least 5 characters long")
    private String description;

    @NotNull(message = "Product condition should not be empty")
    @Size(min = 1, max = 100, message = "Product condition should be at least 1 character long")
    private String condition;

    @NotNull(message = "Date and time should not be empty")
    private String dateTime;

    @NotNull(message = "Image url should not be empty")
    private String imageUrl;

    @NotNull(message = "User Id should not be empty")
    private Long userId;
}