package csd.product;

import csd.app.product.Product;
import csd.app.product.ProductGA;
import csd.app.product.ProductGARepository;
import csd.app.product.ProductNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import csd.app.product.ProductRepository;
import csd.app.product.ProductServiceImpl;
import csd.app.user.User;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductGARepository productsGA;

    @Mock
    private ProductRepository products;

    @InjectMocks
    private ProductServiceImpl productService;

    // test if addProduct gives product if product is valid
    @Test
    void addValidProduct_ReturnSavedProduct() {
        // arrange ***
        User user = new User("tester2", "blabla@hotmail.com",
                                "password");
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setUser(user);
        // mock the "save" operation
        when(products.save(any(Product.class))).thenReturn(product);
        // act ***
        Product savedProduct = productService.addProduct(product);

        // assert ***
        assertNotNull(savedProduct);        
    }

    //test if addProduct returns null if product has no user
    @Test
    void addProduct_NoUser_ReturnNull() {
        // arrange ***
        Product product = new Product("yPhone 15 XXL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 1024GB");        
        // act ***
        Product savedProduct = productService.addProduct(product);

        // assert ***
        assertNull(savedProduct);        
    }

    @Test 
    void getProduct_NotFound_ThrowException() {
        assertThrows(ProductNotFoundException.class, () -> {productService.getProduct(32L);});
    }

    //tests if correct exception thrown ^^
    @Test
    void addValidGA_ReturnsCorrectUserID() {
        // arrange ***
        User user = new User("tester2", "blabla@hotmail.com",
                                "password");
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setUser(user);
        product.setId(23L);
        User user2 = new User("tester23", "blabla2@hotmail.com",
                                "password");
        user2.setId(2L);
        ProductGA prodGA= new ProductGA(23L, 2L);
        product.setProductGA(prodGA);
        // mock the "save" operation
        when(productsGA.save(any(ProductGA.class))).thenReturn(prodGA);
        // act ***
        ProductGA savedProductGA = productService.addProductGA(prodGA);
        // assert ***
        Long savedID = savedProductGA.getReceiverId();
        assertEquals(savedID, user2.getId());        
    }
}