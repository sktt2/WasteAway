package csd.app.payload.request;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class DeleteProductInterestRequest {
	
	@NotNull(message = "Interested User ID cannot be null")
	private Long interestedUserId;

	@NotNull(message = "Product ID cannot be null")
	private Long productId;

	public DeleteProductInterestRequest() {
	}

	public DeleteProductInterestRequest(Long interestedUserId, Long productId) {
		this.interestedUserId = interestedUserId;
		this.productId = productId;
	}

}
