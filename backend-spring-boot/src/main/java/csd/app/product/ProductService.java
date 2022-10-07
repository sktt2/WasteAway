package csd.app.product;

import java.util.List;
import csd.app.user.User;

public interface ProductService {
    List<Product> listProducts();
    Product getProduct(Long id);
    List<Product> getProductsByUser(User user);
    Product addProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(Product product);
    List<ProductGA> listProductGAs();
    ProductGA getProductGA(Long id);
    ProductGA addProductGA(ProductGA productGA);
}
