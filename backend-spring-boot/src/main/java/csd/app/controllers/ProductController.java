package csd.app.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import csd.app.payload.response.MessageResponse;
import csd.app.payload.response.ProductInterestResponse;
import csd.app.payload.response.ProductResponse;
import csd.app.payload.response.UserRecommendationResponse;
import csd.app.payload.request.AddProductInterestRequest;
import csd.app.payload.request.DeleteProductInterestRequest;
import csd.app.payload.request.AddProductRequest;
import csd.app.payload.request.GiveProductRequest;
import csd.app.payload.request.RecommendationRequest;
import csd.app.product.Product;
import csd.app.product.ProductService;
import csd.app.product.ProductGA;
import csd.app.user.ProductInterest;
import csd.app.user.UserRecommendation;

import csd.app.user.SameUserException;
import csd.app.user.User;
import csd.app.user.UserService;

import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/api/products")
    public List<ProductResponse> getProducts() {
        List<Product> products = productService.listProducts();
        List<ProductResponse> resp = new ArrayList<>();
        for (Product product : products) {
            if (productService.getProductGA(product.getId()) == null) {
                ProductResponse prodResp = new ProductResponse(product.getId(), product.getProductName(),
                        product.getCondition(), product.getDateTime(), product.getDescription(), product.getCategory(),
                        product.getImageUrl(), product.getUser());
                resp.add(prodResp);
            }
        }
        return resp;
    }

    @GetMapping("/api/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        ProductResponse resp = new ProductResponse(id, product.getProductName(), product.getCondition(),
                product.getDateTime(), product.getDescription(), product.getCategory(), product.getImageUrl(),
                product.getUser());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/api/products")
    public ResponseEntity<?> addProduct(@Valid @RequestBody AddProductRequest addProductRequest) {
        Product newProduct = new Product(addProductRequest.getProductName(), addProductRequest.getCondition(),
                addProductRequest.getDateTime(), addProductRequest.getCategory(),
                addProductRequest.getDescription(), addProductRequest.getImageUrl());
        User user = userService.getUser(addProductRequest.getUserId());
        newProduct.setUser(user);
        if (productService.addProduct(newProduct) == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("User is not logged in."));
        }
        return ResponseEntity.ok(new MessageResponse("Product registered successfully!"));
    }

    @GetMapping("api/products/user/{id}")
    public List<ProductResponse> getProductByOwner(User user) {
        List<Product> products = productService.getProductsByUser(user);
        List<ProductResponse> resp = new ArrayList<>();
        for (Product product : products) {
            ProductResponse prodResp = new ProductResponse(product.getId(), product.getProductName(),
                    product.getCondition(), product.getDateTime(), product.getDescription(), product.getCategory(),
                    product.getImageUrl(), product.getUser());
            resp.add(prodResp);
        }
        return resp;
    }

    @PutMapping("api/products/update")
    public ResponseEntity<?> updateProductDetail(@RequestBody Product PR) {
        Product product = productService.getProduct(PR.getId());

        // Validation check for updating user details
        try {
            product.setCategory(PR.getCategory());
            product.setCondition(PR.getCondition());
            product.setDateTime(PR.getDateTime());
            product.setDescription(PR.getDescription());
            product.setImageUrl(PR.getImageUrl());
            product.setProductName(PR.getProductName());
            productService.updateProduct(product);
            return ResponseEntity.ok(new MessageResponse("Product detail updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body((new MessageResponse("Error: Invalid Details")));
        }
    }

    @DeleteMapping("api/products/remove/{id}")
    public ResponseEntity<?> removeProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        productService.deleteProduct(product);
        return ResponseEntity.ok(new MessageResponse("Product has been removed"));
    }

    @PostMapping("/api/products/give")
    public ResponseEntity<?> giveProduct(@Valid @RequestBody GiveProductRequest giveProductRequest) {
        Long productId = giveProductRequest.getProductId();
        String receiverUsername = giveProductRequest.getReceiverUsername();
        // Check product exists
        Product product = productService.getProduct(productId);
        User owner = product.getUser();
        // Check receiver exists
        User user = userService.getUserByUsername(receiverUsername);
        if (user.getId() == owner.getId()) {
            throw new SameUserException();
        }
        ProductGA productGA = new ProductGA(productId, user.getId());
        productService.addProductGA(productGA);

        return ResponseEntity.ok(new MessageResponse("Item given successfully"));

    }

    @GetMapping("api/products/give/{id}")
    public List<ProductGA> getGiveAwayByOwner(@PathVariable Long id) {
        List<ProductGA> productGAs = productService.listProductGAs();
        List<ProductGA> resp = new ArrayList<>();
        for (ProductGA productGA : productGAs) {
            Product product = productService.getProduct(productGA.getId());
            // .orElseThrow(() -> new ProductNotFoundException(productGA.getId()));
            if (id == product.getUser().getId()) {
                resp.add(productGA);
            }
        }
        return resp;
    }

    @PostMapping("/api/products/interest")
    public ResponseEntity<?> addProductInterest(@Valid @RequestBody AddProductInterestRequest addProductInterestRequest) {
        String productid = addProductInterestRequest.getProductId();
        String interestedusername = addProductInterestRequest.getInterestedUsername();

        Long longProdId = Long.parseLong(productid);
        //check product exist
        Product product = productService.getProduct(longProdId);
        User owner = product.getUser();
        // make sure owner cant fav their own product
        User interestedUser = userService.getUserByUsername(interestedusername);
        if (interestedUser.getId() == owner.getId()) {
            throw new SameUserException();
        }

        ProductInterest productInterest = new ProductInterest(interestedUser, product);
        userService.addProductInterest(productInterest);
        return ResponseEntity.ok(new MessageResponse("Interest in product added successfully!"));
    }

    @DeleteMapping("api/products/interest/delete")
    public ResponseEntity<?> removeProductInterest(@Valid @RequestBody DeleteProductInterestRequest deleteProductInterestRequest) {
        List<ProductInterest> prodInterests = userService.listProductInterests();
        Long userid = Long.parseLong(deleteProductInterestRequest.getUserId());
        Long productid = Long.parseLong(deleteProductInterestRequest.getProductId());
        Long piid = 0L;
        for (ProductInterest PI : prodInterests) {
            if(PI.getUser().getId() == userid && PI.getProduct().getId() == productid) {
                piid = PI.getProductinterestid();
            }
        }
        ProductInterest productInterest = userService.getProductInterest(piid);
        userService.deleteProductInterest(productInterest);
        return ResponseEntity.ok(new MessageResponse("Interest in product has been removed"));
    }

    @GetMapping("api/products/interests/{id}")
    public List<ProductInterestResponse> getProductInterestsByUser(@PathVariable Long id) {
        Long userid = id; 
        List<ProductInterest> prodInterests = userService.listProductInterests();
        List<ProductInterestResponse> resp = new ArrayList<>();
        for (ProductInterest prodInt : prodInterests) {
            if (prodInt.getUser().getId() == userid) {
                Long prodIntId = prodInt.getProductinterestid();
                Long prodid = prodInt.getProduct().getId();
                ProductInterestResponse piresp = new ProductInterestResponse(prodIntId, userid, prodid);
                resp.add(piresp);
            }
        }
        return resp;
    }

    @GetMapping("api/products/product/interests/{id}")
    public List<String> getProductInterestByProduct(@PathVariable Long id) {
        Long productid = id; 
        List<ProductInterest> prodInterests = userService.listProductInterests();
        List<String> resp = new ArrayList<>();
        for (ProductInterest prodInt : prodInterests) {
            if (prodInt.getProduct().getId() == productid) {
                String username = prodInt.getUser().getUsername();
                resp.add(username);
            }
        }
        return resp;
    }

    // Updates user recommendation setting
    @PostMapping("/api/products/recommendation/update")
    public ResponseEntity<?> updateRecommendation(@RequestBody UserRecommendation UR) {
        UserRecommendation userRecommendation = userService.getRecommendation(UR.getRecommendationId());
        if (UR.getRecommendation() != null && UR.getUser() != null) {
            userRecommendation.setRecommendation(UR.getRecommendation());
            userService.updateRecommendation(userRecommendation);
            return ResponseEntity.ok(new MessageResponse("User recommendation updated successfully"));
        }
        return ResponseEntity.badRequest().body((new MessageResponse("Failed to update user recommendation")));
    }

    /*Implementation for this a bit wonky:
     * If user has no recommendation, will add first 12 products
     * Carousel will show the products that correspond to user recommended category first
     * Will show up to maximum 12 products
     */
    @GetMapping("api/products/product/recommendation/{id}")
    public List<Product> getProductsByUserRecommendation(@PathVariable String category) {
        List<Product> products = productService.listProducts();
        List<Product> resp = new ArrayList<>();
        int countCarouselItems = 0;
        for (Product product : products) {
            if (category.equals("NONE")) {
                resp.add(product);
            } else {
                if (product.getCategory() == category) {
                    resp.add(product);
                }
            }
            ++countCarouselItems;
            if (countCarouselItems > 12) break;
        }
        if (!(category.equals("NONE")) && countCarouselItems < 6) {
            for (Product product : products) {
                if (!(resp.contains(product))) {
                    resp.add(product);
                }
                ++countCarouselItems;
                if (countCarouselItems > 12) break;
            }
        }
        return resp;
    }
}