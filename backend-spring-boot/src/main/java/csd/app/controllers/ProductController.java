package csd.app.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import csd.app.product.Product;
import csd.app.product.ProductRepository;


@RestController
public class ProductController {
    private ProductRepository products;
    
    public ProductController(ProductRepository products){
        this.products =  products;
    }

    @GetMapping("/api/products")
    public List<Product> getProducts(){
        return products.findAll();
    }
}