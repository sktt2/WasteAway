package csd.app.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddProductInterestRequest {
	private String interestedusername;
	private String productid;

	public String getInterestedUsername() {
		return interestedusername;
	}

	public String getProductId() {
		return productid;
	}

	public AddProductInterestRequest(String interestedusername, String productid) {
		this.interestedusername = interestedusername;
		this.productid = productid;
	}
}
