package csd.app.payload.response;

import lombok.*;

@Getter
@Setter

public class ProductInterestResponse {
    private Long id;
    private Long userid;
    private Long productid;

    public ProductInterestResponse(Long id, Long userid, Long productid) {
        this.id = id;
        this.userid = userid;
        this.productid = productid;
    }


}
