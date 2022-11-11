package csd.app.payload.request;

import javax.validation.constraints.*;
import lombok.*;

@Getter
public class ChatRequest {

    @NotNull(message="Taker Id cannot be null")
    private Long takerId;

    @NotNull(message="Owner Id cannot be null")
    private Long ownerId;

    @NotNull(message="Product Id cannot be null")
	private Long productId;
    
}
