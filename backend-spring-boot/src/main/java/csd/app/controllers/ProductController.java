package csd.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import csd.app.payload.response.MessageResponse;
import csd.app.payload.response.ProductInterestResponse;
import csd.app.payload.response.ProductResponse;
import csd.app.payload.request.AddProductInterestRequest;
import csd.app.payload.request.DeleteProductInterestRequest;
import csd.app.payload.request.AddProductRequest;
import csd.app.payload.request.GiveProductRequest;
import csd.app.payload.request.RecommendationRequest;
import csd.app.product.Product;
import csd.app.product.ProductService;
import csd.app.product.ProductGA;
import csd.app.user.ProductInterest;
import csd.app.user.User;
import csd.app.user.UserRecommendation;
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

    // Get all products from database
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
    
    // Get product by productId
    @GetMapping("/api/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Product product = productService.getProduct(id);

        // Check product exist
        if (product == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Product does not exist"));
        }

        ProductResponse resp = new ProductResponse(id, product.getProductName(), product.getCondition(),
                product.getDateTime(), product.getDescription(), product.getCategory(), product.getImageUrl(),
                product.getUser());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/api/products")
    public ResponseEntity<?> addProduct(@Valid @RequestBody AddProductRequest addProductRequest) {

        // Create new product and set to user
        Product newProduct = new Product(addProductRequest.getProductName(), addProductRequest.getCondition(),
                addProductRequest.getDateTime(), addProductRequest.getCategory(),
                addProductRequest.getDescription(), addProductRequest.getImageUrl());
        
        // Check if user exist
        User user = userService.getUser(addProductRequest.getUserId());
        if(user == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("User does not exist"));
        }

        newProduct.setUser(user);
        if (productService.addProduct(newProduct) == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("User is not logged in."));
        }
        return ResponseEntity.ok(new MessageResponse("Product registered successfully!"));
    }

    // Get all products owned by user
    @GetMapping("api/products/user/{id}")
    public ResponseEntity<?> getProductByOwner(User user) {
        // Check user exist
        if (userService.getUser(user.getId()) == null){
            return ResponseEntity.badRequest().body(new MessageResponse("User does not exist"));
        }

        List<Product> products = productService.getProductsByUser(user);
        List<ProductResponse> resp = new ArrayList<>();
        for (Product product : products) {
            ProductResponse prodResp = new ProductResponse(product.getId(), product.getProductName(),
                    product.getCondition(), product.getDateTime(), product.getDescription(), product.getCategory(),
                    product.getImageUrl(), product.getUser());
            resp.add(prodResp);
        }
        return ResponseEntity.ok(resp);
    }

    @PutMapping("api/products/update")
    public ResponseEntity<?> updateProductDetail(@Valid @RequestBody Product PR) {
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
        if (product == null){
            return ResponseEntity.badRequest().body((new MessageResponse("Product not found")));
        }
        productService.deleteProduct(product);
        return ResponseEntity.ok(new MessageResponse("Product has been removed"));
    }

    // Give product from owner to receiver
    @PostMapping("/api/products/give")
    public ResponseEntity<?> giveProduct(@Valid @RequestBody GiveProductRequest giveProductRequest) {
        Long productId = giveProductRequest.getProductId();
        String receiverUsername = giveProductRequest.getReceiverUsername();

        // Check product exists
        Product product = productService.getProduct(productId);
        if (product == null){
            return ResponseEntity.badRequest().body((new MessageResponse("Product does not exist")));
        }
        User owner = product.getUser();

        // Check receiver exists
        User user = userService.getUserByUsername(receiverUsername);
        if (user == null){
            return ResponseEntity.badRequest().body((new MessageResponse("Receiver does not exist")));
        }

        // Owner cannot give item to himself
        if (user.getId() == owner.getId()) {
            return ResponseEntity.badRequest().body((new MessageResponse("Unable to give item to owner")));
        }

        ProductGA productGA = new ProductGA(productId, user.getId());
        productService.addProductGA(productGA);
        return ResponseEntity.ok(new MessageResponse("Item given successfully"));

    }

    // Get all products that have been given away already by the owner
    @GetMapping("api/products/give/{id}")
    public ResponseEntity<?> getGiveAwayByOwner(@PathVariable Long id) {
        if (userService.getUser(id) == null){
            return ResponseEntity.badRequest().body((new MessageResponse("User does not exist")));
        }
        List<ProductGA> productGAs = productService.listProductGAs();
        List<ProductGA> resp = new ArrayList<>();
        for (ProductGA productGA : productGAs) {
            Product product = productService.getProduct(productGA.getId());
            if (id == product.getUser().getId()) {
                resp.add(productGA);
            }
        }
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/api/products/interest")
    public ResponseEntity<?> addProductInterest(@Valid @RequestBody AddProductInterestRequest addProductInterestRequest) {

        Long productId = addProductInterestRequest.getProductId();
        Long interestedUserId = addProductInterestRequest.getInterestedUserId();

        //check product exist
        Product product = productService.getProduct(productId);
        if (product == null){
            return ResponseEntity.badRequest().body((new MessageResponse("Product does not exist")));
        }
        User owner = product.getUser();

        // make sure owner cant fav their own product
        User interestedUser = userService.getUser(interestedUserId);
        if (interestedUserId == owner.getId()) {
            return ResponseEntity.badRequest().body(new MessageResponse("User cannot be interested in own product"));
        }

        ProductInterest productInterest = new ProductInterest(interestedUser, product);
        userService.addProductInterest(productInterest);
        return ResponseEntity.ok(new MessageResponse("Interest in product added successfully!"));

    }

    @DeleteMapping("api/products/interest/delete")
    public ResponseEntity<?> removeProductInterest(@Valid @RequestBody DeleteProductInterestRequest deleteProductInterestRequest) {
        Long userId = deleteProductInterestRequest.getInterestedUserId();
        Long productId = deleteProductInterestRequest.getProductId();
        
        // Product interest does not exist
        ProductInterest productInterest = userService.getProductInterest(productId);
        if (productInterest == null){
            return ResponseEntity.badRequest().body(new MessageResponse("Product interest does not exist"));
        }

        // User does not match the product interest
        if (!productInterest.getUser().getId().equals(userId)){
            return ResponseEntity.badRequest().body(new MessageResponse("Wrong user for this product interest"));
        }
    
        userService.deleteProductInterest(productInterest);
        return ResponseEntity.ok(new MessageResponse("Interest in product has been removed"));
    }

    @GetMapping("api/products/interests/{id}")
    public ResponseEntity<?> getProductInterestsByUser(@PathVariable Long id) {
        Long interestUserId = id; 
        if (userService.getUser(interestUserId) == null){
            return ResponseEntity.badRequest().body(new MessageResponse("User does not exist"));
        }
        
        List<ProductInterest> productInterests = userService.listProductInterests();
        List<ProductInterestResponse> resp = new ArrayList<>();
        for (ProductInterest productInterest : productInterests) {
            if (productInterest.getUser().getId() == interestUserId) {
                Long productInterestId = productInterest.getProductInterestId();
                Long productId = productInterest.getProduct().getId();
                Long ownerId = productInterest.getProduct().getUser().getId();
                String interestedUsername  = productInterest.getUser().getUsername();
                ProductInterestResponse productInterestResponse = new ProductInterestResponse(productInterestId, productId, ownerId, interestUserId, interestedUsername);
                resp.add(productInterestResponse);
            }
        }
        return ResponseEntity.ok(resp);
    }

    @GetMapping("api/products/product/interests/{id}")
    public ResponseEntity<?> getProductInterestByProduct(@PathVariable Long id) {
        Long productId = id;
        List<ProductInterest> productInterests = userService.listProductInterests();
        List<ProductInterestResponse> resp = new ArrayList<>();
        for (ProductInterest productInterest : productInterests) {
            if (productInterest.getProduct().getId() == productId) {
                Long interestUserId = productInterest.getUser().getId();
                Long productInterestId = productInterest.getProductInterestId();
                Long ownerId = productInterest.getProduct().getUser().getId();
                String interestedUsername  = productInterest.getUser().getUsername();
                ProductInterestResponse productInterestResponse = new ProductInterestResponse(productInterestId, productId, ownerId, interestUserId, interestedUsername);
                resp.add(productInterestResponse);
            }
        }
        return ResponseEntity.ok(resp);
    }

    // Update user recommendation
    @PutMapping("/api/products/recommendation/update")
    public ResponseEntity<?> updateRecommendation(@Valid @RequestBody RecommendationRequest UR) {
        String username = UR.getUsername();

        User user = userService.getUserByUsername(username);
        if (user == null){
            return ResponseEntity.badRequest().body(new MessageResponse("User does not exist"));
        }
        
        UserRecommendation userRecommendation = new UserRecommendation();
        userRecommendation.setUserId(user.getId());
        userRecommendation.setRecommendation(UR.getRecommendation());
        userService.updateRecommendation(userRecommendation);
        user.setFirstTime(false);
        userService.updateUser(user);
        return ResponseEntity.ok(new MessageResponse("User recommendation updated successfully"));
        

    }

    // Carousel will show products matching user recommended category first
    @GetMapping("api/products/recommendation/{id}")
    public ResponseEntity<?> getProductsByUserRecommendation(@PathVariable Long id) {
        UserRecommendation userRecommendation =  userService.getRecommendation(id);
        if (userRecommendation == null){
            return ResponseEntity.badRequest().body(new MessageResponse("User recommendation does not exist"));
        }
        String recommendation = userRecommendation.getRecommendation();

        return ResponseEntity.ok(recommendation);
    }

    @GetMapping("api/products/give")
    public ResponseEntity<?> getBooleanIfProductGAExist(@RequestParam("productId") @PathVariable Long productId) {
        ProductGA productGA = productService.getProductGA(productId);
        if (productGA == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Product give away does not exist"));
        }
        return ResponseEntity.ok(true);
    }

}