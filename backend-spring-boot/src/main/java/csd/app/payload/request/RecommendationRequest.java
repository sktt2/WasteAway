package csd.app.payload.request;

import java.util.Set;
import javax.validation.constraints.*;

import csd.app.product.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecommendationRequest {
    @NotNull
    private String recommendation;

    private String username;

    private Set<Product> products;

    public String getRecommendation() {
        return recommendation;
    }

    public String getUsername() {
        return username;
    }

    public Set<Product> getProducts() {
        return products;
    }
 
    public RecommendationRequest() {

    }

    public RecommendationRequest(String recommendation, String username, Set<Product> products) {
        this.recommendation = recommendation;
        this.username = username;
        this.products = products;
    }
}