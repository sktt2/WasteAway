package csd.app.product;

import java.util.List;

import org.springframework.web.bind.annotation.*;


@RestController
public class ProductController {
    private ProductRepository products;
    
    public ProductController(ProductRepository products){
        this.products =  products;
    }

    @GetMapping("/products")
    public List<Product> getProducts(){
        return products.findAll();
    }
}