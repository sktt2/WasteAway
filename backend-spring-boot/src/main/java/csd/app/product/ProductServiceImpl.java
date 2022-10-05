package csd.app.product;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ProductServiceImpl implements ProductService {
    
    @Autowired
    private ProductRepository products;

    public List<Product> listProducts() {
        return products.findAll();
    }

    public Product getProduct(Long id) {
        return products.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public Product addProduct(Product product) {
        if (product.getUser() == null) {
            return null;
        }
        return products.save(product);
    }
}
