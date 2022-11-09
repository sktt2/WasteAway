package csd.app.payload.request;

import javax.validation.constraints.*;

import lombok.*;

@Getter
@Setter

public class GiveProductRequest {

	@NotBlank(message = "Receiver username cannot not be empty")
	private String receiverUsername;

	@NotNull(message = "Product Id cannot not be null")
	private Long productId;

	public GiveProductRequest() {

	}
	
	public GiveProductRequest(String receiverUsername, Long productId) {
		this.receiverUsername = receiverUsername;
		this.productId = productId;
	}
}
