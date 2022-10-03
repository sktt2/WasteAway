package csd.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import csd.app.payload.response.MessageResponse;
import csd.app.payload.response.ProductResponse;
import csd.app.payload.request.AddProductRequest;
import csd.app.product.Product;
import csd.app.product.ProductGA;
import csd.app.product.ProductGARepository;
import csd.app.product.ProductNotFoundException;
import csd.app.product.ProductRepository;
import csd.app.user.User;
import csd.app.user.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(ProductRepository products, UserRepository users) {
        this.productRepository = products;
        this.userRepository = users;
    }

    @Autowired
    ProductGARepository productGARepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/api/products")
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> resp = new ArrayList<>();
        for (Product product : products) {
            ProductResponse prodResp = new ProductResponse(product.getId(), product.getProductName(),
                    product.getCondition(), product.getDateTime(), product.getDescription(), product.getCategory(),
                    product.getImageUrl(), product.getUser());
            resp.add(prodResp);
        }
        return resp;
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        ProductResponse resp = new ProductResponse(id, product.getProductName(), product.getCondition(),
                product.getDateTime(), product.getDescription(), product.getCategory(), product.getImageUrl(),
                product.getUser());
        return ResponseEntity.ok(resp);
    }

    @GetMapping("api/products/user/{id}")
    public List<ProductResponse> getProductByOwner(User user) {
        List<Product> products = productRepository.findByUser(user);
        List<ProductResponse> resp = new ArrayList<>();
        for (Product product : products) {
            ProductResponse prodResp = new ProductResponse(product.getId(), product.getProductName(),
                    product.getCondition(), product.getDateTime(), product.getDescription(), product.getCategory(),
                    product.getImageUrl(), product.getUser());
            resp.add(prodResp);
        }
        return resp;
    }

    @PutMapping("api/products/update/{id}")
    public ResponseEntity<?> updateProductDetail(@RequestBody Product PR) {
        Product product = productRepository.findById(PR.getId())
                .orElseThrow(() -> new ProductNotFoundException(PR.getId()));
        if (PR.getCategory() != null && PR.getCondition() != null && PR.getDateTime() != null &&
                PR.getImageUrl() != null && PR.getProductName() != null) {
            product.setCategory(PR.getCategory());
            product.setCondition(PR.getCondition());
            product.setDateTime(PR.getDateTime());
            product.setDescription(PR.getDescription());
            product.setImageUrl(PR.getImageUrl());
            product.setProductName(PR.getProductName());
            productRepository.save(product);
            return ResponseEntity.ok(new MessageResponse("Product detail updated successfully"));
        }
        return ResponseEntity.ok(new MessageResponse("Failed to update product detail"));
    }

    @DeleteMapping("api/products/remove/{id}")
    public ResponseEntity<?> removeProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
        return ResponseEntity.ok(new MessageResponse("Product has been removed"));
    }

    @PostMapping("/api/products/give")
    public ResponseEntity<?> giveProduct(@RequestBody ProductGA pGA) {
        Long productId = pGA.getId();
        Long receiverId = pGA.getReceiverId();

        // Check product exists
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        // Check receiver exists
        User user = userRepository.findById(receiverId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        ProductGA productGA = new ProductGA(productId, receiverId);
        productGARepository.save(productGA);

        return ResponseEntity.ok(new MessageResponse("Item given successfully"));
    }
}