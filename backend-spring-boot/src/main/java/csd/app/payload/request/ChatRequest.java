package csd.app.payload.request;

import javax.validation.constraints.*;
import lombok.*;

@Getter
public class ChatRequest {

    @NotBlank(message="Taker Id cannot be empty or null")
    private Long takerId;

    @NotBlank(message="Owner Id cannot be empty or null")
    private Long ownerId;

    @NotBlank(message="Product Id cannot be empty or null")
	private Long productId;
    
}
