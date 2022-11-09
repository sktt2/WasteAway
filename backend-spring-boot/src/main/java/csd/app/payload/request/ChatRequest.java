package csd.app.payload.request;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class ChatRequest {

    @NotBlank(message="Taker ID cannot be empty")
    private Long takerId;

    @NotBlank(message="Product ID should not be empty")
	private Long productId;
    
}
