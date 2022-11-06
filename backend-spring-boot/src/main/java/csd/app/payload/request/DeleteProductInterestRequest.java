package csd.app.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteProductInterestRequest {
	private Long interestedUserId;
	private Long productId;

	public DeleteProductInterestRequest() {
	}

	public DeleteProductInterestRequest(Long interestedUserId, Long productId) {
		this.interestedUserId = interestedUserId;
		this.productId = productId;
	}

}
