package csd.app.payload.request;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class ChatRequest {

    @NotNull(message="Taker ID cannot be empty")
    private Long takerId;

    @NotNull(message="Product ID should not be empty")
	private Long productId;
    
}
