package csd.app.payload.request;

import javax.validation.constraints.*;

import lombok.*;

@Getter
@Setter

public class GiveProductRequest {
	@NotBlank (message = "Receiver username should not be empty")
	private String receiverUsername;

	@NotBlank (message = "Product ID should not be empty")
	private Long productId;

	public GiveProductRequest() {

	}
	
	public GiveProductRequest(String receiverUsername, Long productId) {
		this.receiverUsername = receiverUsername;
		this.productId = productId;
	}
}
