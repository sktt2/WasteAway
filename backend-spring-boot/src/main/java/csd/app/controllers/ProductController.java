package csd.app.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import csd.app.product.Product;
import csd.app.product.ProductNotFoundException;
import csd.app.product.ProductRepository;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(ProductRepository products) {
        this.productRepository = products;
    }

    @GetMapping("/api/products")
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/api/products/{id}")
    public Product getProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
        return product;
    }

    @PostMapping("/api/products")
    public Product addProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

}