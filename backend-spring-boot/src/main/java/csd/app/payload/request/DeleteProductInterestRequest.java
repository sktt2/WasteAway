package csd.app.payload.request;

import com.mysql.cj.x.protobuf.MysqlxCrud.Delete;

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

	public DeleteProductInterestRequest() {
		
	}

	public DeleteProductInterestRequest(String userid, String productid) {
		this.userid = userid;
		this.productid = productid;
	}

}
