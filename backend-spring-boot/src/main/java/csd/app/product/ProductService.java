package csd.app.product;

import java.util.List;

public interface ProductService {
    List<Product> listProducts();
    Product getProduct(Long id);
    Product addProduct(Product product);
}
