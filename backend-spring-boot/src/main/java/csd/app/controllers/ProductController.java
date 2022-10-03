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
import csd.app.payload.request.GiveProductRequest;
import csd.app.product.Product;
import csd.app.product.ProductGA;
import csd.app.product.ProductGARepository;
import csd.app.product.ProductNotFoundException;
import csd.app.product.ProductRepository;
import csd.app.user.SameUserException;
import csd.app.user.User;
import csd.app.user.UserRepository;

import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

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

    @PostMapping("/api/products")
    public Product addProduct(@Valid @RequestBody AddProductRequest addProductRequest) {
        Product newProduct = new Product(addProductRequest.getProductName(), addProductRequest.getCondition(),
                addProductRequest.getDateTime(), addProductRequest.getCategory(),
                addProductRequest.getDescription());
        User user = userRepository.findById(addProductRequest.getUserId()).get();
        newProduct.setUser(user);
        return productRepository.save(newProduct);
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
    public ResponseEntity<?> giveProduct(@Valid @RequestBody GiveProductRequest giveProductRequest) {
        Long productId = giveProductRequest.getProductId();
        String receiverUsername = giveProductRequest.getReceiverUsername();

        // Check product exists
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        User owner = product.getUser();
        // Check receiver exists
        User user = userRepository.findByUsername(receiverUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getId() == owner.getId()) {
            throw new SameUserException();
        }
        ProductGA productGA = new ProductGA(productId, user.getId());
        productGARepository.save(productGA);

        return ResponseEntity.ok(new MessageResponse("Item given successfully"));

    }

}