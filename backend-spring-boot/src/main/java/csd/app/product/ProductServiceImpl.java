package csd.app.product;

import java.util.List;

import csd.app.exception.ProductNotFoundException;
import csd.app.exception.UserNotFoundException;
import csd.app.user.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository products;

    @Autowired
    private ProductGARepository productGAs;

    // Get all products in database
    public List<Product> listProducts() {
        return products.findAll();
    }

    public Product getProduct(Long id) {
        return products.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    // Get all products from user in database
    public List<Product> getProductsByUser(User user) {
        return products.findByUser(user);
    }

    // Save product into database and return product with productId generated
    public Product addProduct(Product product) {
        if (product.getUser() == null) {
            throw new UserNotFoundException();
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

    // Get all products that have been given away
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

    // Save product given away into database and return product with productId generated
    public ProductGA addProductGA(ProductGA productGA) {
        return productGAs.save(productGA);
    }
}
