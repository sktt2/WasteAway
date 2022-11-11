package csd.app.payload.request;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductInterestRequest {

	@NotNull(message = "User Id cannot be empty")
	private Long interestedUserId;

	@NotNull(message="Product Id cannot be empty")
	private Long productId;


	public AddProductInterestRequest() {
	}

	public AddProductInterestRequest(Long interestedUserId, Long productId) {
		this.interestedUserId = interestedUserId;
		this.productId = productId;
	}
}
