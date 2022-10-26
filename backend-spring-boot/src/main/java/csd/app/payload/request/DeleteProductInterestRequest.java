package csd.app.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteProductInterestRequest {
	private String userid;
	private String productid;

	public String getUserId() {
		return userid;
	}

	public String getProductId() {
		return productid;
	}

	public DeleteProductInterestRequest(String userid, String productid) {
		this.userid = userid;
		this.productid = productid;
	}

}
