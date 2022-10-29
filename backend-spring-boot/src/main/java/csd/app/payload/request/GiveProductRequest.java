package csd.app.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GiveProductRequest {
	private String receiverUsername;
	private Long productId;

	public GiveProductRequest() {

	}
	
	public GiveProductRequest(String receiverUsername, Long productId) {
		this.receiverUsername = receiverUsername;
		this.productId = productId;
	}
}
