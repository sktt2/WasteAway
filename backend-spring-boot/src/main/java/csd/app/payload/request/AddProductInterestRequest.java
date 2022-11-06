package csd.app.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductInterestRequest {
	private Long interestedUserId;
	private Long productId;


	public AddProductInterestRequest() {
	}

	public AddProductInterestRequest(Long interestedUserId, Long productId) {
		this.interestedUserId = interestedUserId;
		this.productId = productId;
	}
}
