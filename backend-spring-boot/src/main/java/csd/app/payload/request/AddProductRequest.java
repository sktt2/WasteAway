package csd.app.payload.request;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter

public class AddProductRequest {
    
    @NotBlank
    private String productName;

    @NotBlank
    private String category;

    @NotBlank
    @Size(min = 5, max = 50)
    private String description;

    @NotBlank
    @Size(max = 50)
    private String condition;

    @NotBlank
    private String dateTime;

    @NotBlank
    private String imageUrl;

    private Long userId;
}
