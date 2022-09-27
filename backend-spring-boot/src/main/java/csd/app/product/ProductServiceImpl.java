package csd.app.product;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService{
    private ProductRepository products;
    
    public ProductServiceImpl(ProductRepository products){
        this.products = products;
    }

    @Override
    public List<Product> getProducts() {
        return products.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return products.findById(id).orElse(null);
    }
}
