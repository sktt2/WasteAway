package csd.app.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import csd.app.product.Product;
import csd.app.product.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProductController {
    private ProductService productService;
    
    public ProductController(ProductService products){
        this.productService =  products;
    }

    @GetMapping("/api/products")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("/api/products/{id}")
    public Product getProduct(@PathVariable Long id) throws Exception{
        Product prod = productService.getProduct(id);
        if (prod == null) throw new Exception("Product not found");
        return prod;
    }

}