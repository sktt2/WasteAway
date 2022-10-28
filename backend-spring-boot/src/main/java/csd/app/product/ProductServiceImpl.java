package csd.app.product;

import java.util.List;
import csd.app.user.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository products;

    @Autowired
    private ProductGARepository productGAs;

    public List<Product> listProducts() {
        return products.findAll();
    }

    public Product getProduct(Long id) {
        return products.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public List<Product> getProductsByUser(User user) {
        return products.findByUser(user);
    }

    public Product addProduct(Product product) {
        if (product.getUser() == null) {
            return null;
        }
        return products.save(product);
    }

    public Product updateProduct(Product product) {
        this.getProduct(product.getId());
        return products.save(product);
    }

    public void deleteProduct(Product product) {
        products.delete(product);
    }

    public List<ProductGA> listProductGAs() {
        return productGAs.findAll();
    }

    public ProductGA getProductGA(Long id) {
        if (productGAs.existsById(id)) {
            return productGAs.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Product not found."));
        }
        return null;
    }

    public ProductGA addProductGA(ProductGA productGA) {
        return productGAs.save(productGA);
    }
}
