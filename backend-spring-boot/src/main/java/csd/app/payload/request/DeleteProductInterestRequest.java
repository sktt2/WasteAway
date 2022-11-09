package csd.app.payload.request;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class DeleteProductInterestRequest {
	@NotBlank (message = "Interested User ID should not be empty")
	private Long interestedUserId;

	@NotBlank (message = "Product ID should not be empty")
	private Long productId;

	public DeleteProductInterestRequest() {
	}

	public DeleteProductInterestRequest(Long interestedUserId, Long productId) {
		this.interestedUserId = interestedUserId;
		this.productId = productId;
	}

}
