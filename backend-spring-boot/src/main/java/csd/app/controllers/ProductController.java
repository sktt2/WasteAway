package csd.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import csd.app.payload.response.ProductResponse;
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
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> resp = new ArrayList<>();
        for (Product product : products) {
            ProductResponse prodResp = new ProductResponse(product.getId(), product.getProductName(), product.getCondition(), product.getDateTime(), product.getDescription(), product.getCategory(), product.getImageUrl(),product.getUser());
            resp.add(prodResp);
        }
        return resp;
    }

    @GetMapping("/api/products/{id}")
    public  ResponseEntity<?> getProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
        ProductResponse resp = new ProductResponse(id, product.getProductName(), product.getCondition(), product.getDateTime(), product.getDescription(), product.getCategory(), product.getImageUrl(), product.getUser());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/api/products")
    public Product addProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

}