package csd.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import csd.app.payload.response.ProductInterestResponse;
import csd.app.product.Product;
import csd.app.product.ProductRepository;
import csd.app.user.User;
import csd.app.user.UserRepository;
import csd.app.user.UserInfo;
import csd.app.user.UserInfoRepository;
import csd.app.user.ProductInterest;
import csd.app.user.ProductInterestRepository;
import csd.app.user.UserService;

/** Start an actual HTTP server listening at a random port */
@SpringBootTest(classes = csd.app.Main.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    private final String baseUrl = "http://localhost:";

    @Autowired
    /**
     * Use TestRestTemplate for testing a real instance of your application as an
     * external actor.
     * TestRestTemplate is just a convenient subclass of RestTemplate that is
     * suitable for integration tests.
     * It is fault tolerant, and optionally can carry Basic authentication headers.
     */
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository products;

    @Autowired
    private UserRepository users;

    @Autowired
    private UserInfoRepository userInfos;

    @Autowired
    private ProductInterestRepository productInterests;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserService userService;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void tearDown() {
        // clear the database after each test
        productInterests.deleteAll();
        products.deleteAll();
        userInfos.deleteAll();
        users.deleteAll();
        // responses.deleteAll();
    }

    @Test
    public void getProducts_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/products");
        User user = new User("tester1", "abc@test.com", encoder.encode("password"));
        users.save(user);
        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), "SINGAPORE 11111", 87231231);
        userInfos.save(userInfo);
        Product product = new Product("PHONE", "NEW", LocalDateTime.now().toString(), "ELECTRONICS",
                "This is the description");
        product.setImageUrl(
                "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        product.setUser(user);
        products.save(product);

        ResponseEntity<Product[]> result = restTemplate.getForEntity(uri,
                Product[].class);
        Product[] productArr = result.getBody();

        assertNotNull(productArr);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, productArr.length);
    }

    @Test
    public void getProduct_ValidProductId_Success() throws Exception {
        User user = new User("tester2", "blabla@hotmail.com",
                encoder.encode("password"));
        users.save(user);
        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), "SINGAPORE 511111", 87231231);
        userInfos.save(userInfo);
        Product product = new Product("CAMERA", "OLD",
                LocalDateTime.now().toString(), "ELECTRONICS", "TestDescription");
        product.setImageUrl(
                "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        product.setUser(user);
        Long id = products.save(product).getId();
        URI uri = new URI(baseUrl + port + "/api/products/" + id);

        ResponseEntity<Product> result = restTemplate.getForEntity(uri,
                Product.class);
        Product validProduct = result.getBody();

        assertNotNull(validProduct);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(product.getProductName(), validProduct.getProductName());
    }

    @Test
    public void getProduct_InvalidProductId_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/products/4");

        ResponseEntity<Product> result = restTemplate.getForEntity(uri,
                Product.class);

        assertEquals(400, result.getStatusCode().value());
    }

    @Test
    public void getProductByOwner_Success() throws Exception {
        User user = new User("tester1", "abc@test.com", encoder.encode("password"));
        users.save(user);
        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(),
                "SINGAPORE1234567", 87231231);
        userInfos.save(userInfo);
        Product product = new Product("PHONE", "NEW", LocalDateTime.now().toString(),
                "ELECTRONICS", "this is a description");
        product.setImageUrl(
                "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        Product product2 = new Product("LAPTOP", "OLD",
                LocalDateTime.now().toString(),
                "ELECTRONICS", "this is a description");
        product2.setImageUrl(
                "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        Product product3 = new Product("TABLET", "NEW",
                LocalDateTime.now().toString(),
                "ELECTRONICS", "this is a description");
        product3.setImageUrl(
                "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        product.setUser(user);
        product2.setUser(user);
        product3.setUser(user);
        products.save(product);
        products.save(product2);
        products.save(product3);

        Long userId = user.getId();
        URI uri = new URI(baseUrl + port + "/api/products/user/" + userId);

        ResponseEntity<Product[]> result = restTemplate.getForEntity(uri,
                Product[].class);
        Product[] productArr = result.getBody();

        assertNotNull(productArr);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(3, productArr.length);
    }

    @Test
    public void addProduct_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/products");
        User user = new User("tester2", "blabla@hotmail.com",
                encoder.encode("password"));
        users.save(user);

        JSONObject productJsonObject = new JSONObject();
        productJsonObject.put("productName", "CAMERA");
        productJsonObject.put("condition", "NEW");
        productJsonObject.put("dateTime", LocalDateTime.now().toString());
        productJsonObject.put("category", "ELECTRONICS");
        productJsonObject.put("description", "Test Description");
        productJsonObject.put("imageUrl",
                "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-3gs-ofic.jpg");
        productJsonObject.put("userId", user.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(productJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request,
                String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Product registered successfully!",
                root.path("message").asText());
    }

    @Test
    public void addProduct_NameTooLong_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/products");
        User user = new User("tester2", "blabla@hotmail.com",
                        encoder.encode("password"));
        users.save(user);

        //JSONObject created with productName > 100 char long
        JSONObject productJsonObject = new JSONObject();
        productJsonObject.put("productName", "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz");
        productJsonObject.put("condition", "NEW");
        productJsonObject.put("dateTime", LocalDateTime.now().toString());
        productJsonObject.put("category", "ELECTRONICS");
        productJsonObject.put("description", "Test Description");
        productJsonObject.put("imageUrl",
                        "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-3gs-ofic.jpg");
        productJsonObject.put("userId", user.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(productJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request,
                        String.class);
        JsonNode root = objectMapper.readTree(result.getBody());
        
        assertEquals(400, result.getStatusCode().value());
        assertEquals("Product name should be between 1-100 character long",
                        root.path("message").asText());
    }

    @Test
    public void addProduct_DescTooShort_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/products");
        User user = new User("tester2", "blabla@hotmail.com",
                        encoder.encode("password"));
        users.save(user);

        //JSONObject is created with description < 5 char long
        JSONObject productJsonObject = new JSONObject();
        productJsonObject.put("productName", "CAMERA");
        productJsonObject.put("condition", "NEW");
        productJsonObject.put("dateTime", LocalDateTime.now().toString());
        productJsonObject.put("category", "ELECTRONICS");
        productJsonObject.put("description", "less");
        productJsonObject.put("imageUrl",
                        "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-3gs-ofic.jpg");
        productJsonObject.put("userId", user.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(productJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request,
                        String.class);
        JsonNode root = objectMapper.readTree(result.getBody());
        
        assertEquals(400, result.getStatusCode().value());
        assertEquals("Product description should be between 5-200 characters long",
                        root.path("message").asText());
    }

    @Test
    public void addProduct_MissingProdName_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/products");
        User user = new User("tester2", "blabla@hotmail.com",
                        encoder.encode("password"));
        users.save(user);

        //JsonObject is built without a description field
        JSONObject productJsonObject = new JSONObject();
        productJsonObject.put("condition", "NEW");
        productJsonObject.put("dateTime", LocalDateTime.now().toString());
        productJsonObject.put("category", "ELECTRONICS");
        productJsonObject.put("description", "Test Description");
        productJsonObject.put("imageUrl",
                        "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-3gs-ofic.jpg");
        productJsonObject.put("userId", user.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(productJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request,
                        String.class);
        JsonNode root = objectMapper.readTree(result.getBody());
        
        assertEquals(400, result.getStatusCode().value());
        assertEquals("Product name should not be empty",
                        root.path("message").asText());
    }

    @Test
    public void addProduct_MissingDesc_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/products");
        User user = new User("tester2", "blabla@hotmail.com",
                        encoder.encode("password"));
        users.save(user);

        //JsonObject is built without a description field
        JSONObject productJsonObject = new JSONObject();
        productJsonObject.put("productName", "CAMERA");
        productJsonObject.put("condition", "NEW");
        productJsonObject.put("dateTime", LocalDateTime.now().toString());
        productJsonObject.put("category", "ELECTRONICS");
        productJsonObject.put("imageUrl",
                        "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-3gs-ofic.jpg");
        productJsonObject.put("userId", user.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(productJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request,
                        String.class);
        JsonNode root = objectMapper.readTree(result.getBody());
        
        assertEquals(400, result.getStatusCode().value());
        assertEquals("Product description should not be empty",
                        root.path("message").asText());
    }

    @Test
    public void addProduct_MissingCategory_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/products");
        User user = new User("tester2", "blabla@hotmail.com",
                        encoder.encode("password"));
        users.save(user);

        //JsonObject is built without a description field
        JSONObject productJsonObject = new JSONObject();
        productJsonObject.put("productName", "CAMERA");
        productJsonObject.put("condition", "NEW");
        productJsonObject.put("dateTime", LocalDateTime.now().toString());
        productJsonObject.put("description", "Test Description");
        productJsonObject.put("imageUrl",
                        "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-3gs-ofic.jpg");
        productJsonObject.put("userId", user.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(productJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request,
                        String.class);
        JsonNode root = objectMapper.readTree(result.getBody());
        
        assertEquals(400, result.getStatusCode().value());
        assertEquals("Category should not be empty",
                        root.path("message").asText());
    }

    @Test
    public void addProduct_MissingCondition_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/products");
        User user = new User("tester2", "blabla@hotmail.com",
                        encoder.encode("password"));
        users.save(user);

        //JsonObject is built without a description field
        JSONObject productJsonObject = new JSONObject();
        productJsonObject.put("productName", "CAMERA");
        productJsonObject.put("dateTime", LocalDateTime.now().toString());
        productJsonObject.put("category", "ELECTRONICS");
        productJsonObject.put("description", "Test Description");
        productJsonObject.put("imageUrl",
                "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-3gs-ofic.jpg");
        productJsonObject.put("userId", user.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(productJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request,
                        String.class);
        JsonNode root = objectMapper.readTree(result.getBody());
        
        assertEquals(400, result.getStatusCode().value());
        assertEquals("Product condition should not be empty",
                        root.path("message").asText());
    }

    @Test
    public void addProduct_MissingImage_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/products");
        User user = new User("tester2", "blabla@hotmail.com",
                        encoder.encode("password"));
        users.save(user);

        //JsonObject is built without a description field
        JSONObject productJsonObject = new JSONObject();
        productJsonObject.put("productName", "CAMERA");
        productJsonObject.put("condition", "NEW");
        productJsonObject.put("dateTime", LocalDateTime.now().toString());
        productJsonObject.put("category", "ELECTRONICS");
        productJsonObject.put("description", "Test Description");
        productJsonObject.put("userId", user.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(productJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request,
                        String.class);
        JsonNode root = objectMapper.readTree(result.getBody());
        
        assertEquals(400, result.getStatusCode().value());
        assertEquals("Image url should not be empty",
                        root.path("message").asText());
    }

    @Test
    public void deleteProduct_ValidProductId_Success() throws Exception {
        User user = new User("tester2", "blabla@hotmail.com",
                encoder.encode("password"));
        users.save(user);
        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(),
                "SINGAPORE01234567", 87231231);
        userInfos.save(userInfo);
        Product product = new Product("CAMERA", "OLD",
                LocalDateTime.now().toString(), "ELECTRONICS", "TestDescription");
        product.setImageUrl(
                "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        product.setUser(user);
        products.save(product);
        Long id = product.getId();
        URI uri = new URI(baseUrl + port + "/api/products/remove/" + id);

        ResponseEntity<Void> result = restTemplate.exchange(uri, HttpMethod.DELETE,
                null, Void.class);

        assertEquals(200, result.getStatusCode().value());
        Optional<Product> emptyValue = Optional.empty();
        assertEquals(emptyValue, products.findById(product.getId()));
    }

    @Test
    public void deleteProduct_InvalidProductId_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/products/remove/4");

        ResponseEntity<Void> result = restTemplate.exchange(uri, HttpMethod.DELETE,
                null, Void.class);

        assertEquals(400, result.getStatusCode().value());
    }

    @Test
    public void updateProduct_ValidProductId_Success() throws Exception {
        User user = new User("tester2", "blabla@hotmail.com",
                encoder.encode("password"));
        users.save(user);
        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(),
                "SINGAPORE01234567", 87231231);
        userInfos.save(userInfo);

        Product product = new Product("PHONE", "OLD",
                LocalDateTime.now().toString(), "ELECTRONICS", "TestDescription");
        product.setImageUrl("https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-3gs-ofic.jpg");
        product.setUser(user);
        products.save(product);

        product.setProductName("WATER BOTTLE");
        product.setDateTime(LocalDateTime.now().toString());
        product.setCategory("UTILITY");
        product.setCondition("NEW");
        product.setDescription("A WATER BOTTLE");
        product.setImageUrl(
                "https://store.storeimages.cdn-apple.com/8756/as-images.apple.com/is/HQ222?wid=1649&hei=2207&fmt=jpeg&q4034080576lt=95&.v=165");
        Long updatedId = product.getId();

        URI uri = new URI(baseUrl + port + "/api/products/update");

        HttpEntity<Product> resp = new HttpEntity<Product>(product);
        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT,
                resp, String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        Product updatedProduct = products.findById(updatedId).get();

        assertEquals(200, result.getStatusCode().value());
        assertEquals("Product detail updated successfully", root.path("message").asText());
        assertEquals(product.getProductName(), updatedProduct.getProductName());
    }

    @Test
    public void updateProduct_InvalidProductId_Failure() throws Exception {
        User user = new User("tester2", "blabla@hotmail.com",
                encoder.encode("password"));
        users.save(user);
        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(),
                "SINGAPORE01234567", 87231231);
        userInfos.save(userInfo);

        Product product = new Product("PHONE", "OLD",
                LocalDateTime.now().toString(), "ELECTRONICS", "TestDescription");
        product.setImageUrl("https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-3gs-ofic.jpg");
        product.setUser(user);
        products.save(product);
        product.setId(999l);

        URI uri = new URI(baseUrl + port + "/api/products/update/");

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT,
                new HttpEntity<Product>(product), String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Product not found: 999", root.path("message").asText());
    }

    @Test
    public void addProductInterest_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/products/interest");
        User user = new User("tester2", "blabla@hotmail.com", encoder.encode("password1"));
        users.save(user);
        UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), "SINGAPORE01234567", 87231231);
        userInfos.save(userInfo);

        User owner = new User("tester3", "test@email.com", encoder.encode("password123"));
        users.save(owner);
        UserInfo ownerInfo = new UserInfo(owner.getId(), owner.getUsername(), "123 Test Street", 98765432);
        userInfos.save(ownerInfo);
  
        Product product = new Product("PHONE", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "this is a description");
        product.setImageUrl("https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        product.setUser(owner);
        products.save(product);

        JSONObject productInterestJsonObject = new JSONObject();
        productInterestJsonObject.put("interestedUserId", user.getId());
        productInterestJsonObject.put("productId", product.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(productInterestJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        JsonNode root = objectMapper.readTree(result.getBody());
 
        assertEquals(200, result.getStatusCode().value());
        assertEquals("Interest in product added successfully!", root.path("message").asText());
    }

    @Test
    public void getProductInterestsByUser_Success() throws Exception {
    User user = new User("tester2", "blabla@hotmail.com", encoder.encode("password1"));
    users.save(user);
    UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(), "SINGAPORE01234567", 87231231);
    userInfos.save(userInfo);

    User owner = new User("tester3", "test@email.com",
    encoder.encode("password123"));
    users.save(owner);
    UserInfo ownerInfo = new UserInfo(owner.getId(), owner.getUsername(), "123 Test Street", 98765432);
    userInfos.save(ownerInfo);

    Product product1 = new Product("PHONE", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "this is a description");
    product1.setImageUrl("https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
    Product product2 = new Product("LAPTOP", "OLD",LocalDateTime.now().toString(), "ELECTRONICS", "this is a description");
    product2.setImageUrl("https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
    Product product3 = new Product("TABLET", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "this is a description");
    product3.setImageUrl("https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
    product1.setUser(owner);
    product2.setUser(owner);
    product3.setUser(owner);
    products.save(product1);
    products.save(product2);
    products.save(product3);

    ProductInterest interest1 = new ProductInterest(user, product1);
    ProductInterest interest2 = new ProductInterest(user, product2);
    ProductInterest interest3 = new ProductInterest(user, product3);

    userService.addProductInterest(interest1);
    userService.addProductInterest(interest2);
    userService.addProductInterest(interest3);

    URI uri = new URI(baseUrl + port + "/api/products/interests/" + user.getId());

    ResponseEntity<ProductInterestResponse[]> result =
    restTemplate.getForEntity(uri, ProductInterestResponse[].class);
    ProductInterestResponse[] respArr = result.getBody();

    assertNotNull(respArr);
    assertEquals(200, result.getStatusCode().value());
    assertEquals(3, respArr.length);
    }



    @Test
    public void getProductInterestsByProduct_Success() throws Exception {
    User owner = new User("owner", "test@email.com",
    encoder.encode("password123"));
    users.save(owner);
    UserInfo ownerInfo = new UserInfo(owner.getId(), owner.getUsername(), "123 Test Street", 98765432);
    userInfos.save(ownerInfo);

    Product product = new Product("PHONE", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "this is a description");
    product.setImageUrl("https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
    product.setUser(owner);
    products.save(product);

    User user1 = new User("tester1", "blabla1@hotmail.com", encoder.encode("password1"));
    users.save(user1);
    UserInfo userInfo1 = new UserInfo(user1.getId(), user1.getUsername(), "SINGAPORE01234567", 87231231);
    userInfos.save(userInfo1);
    User user2 = new User("tester2", "blabla2@hotmail.com", encoder.encode("password1"));
    users.save(user2);
    UserInfo userInfo2 = new UserInfo(user2.getId(), user2.getUsername(), "SINGAPORE01234567", 87231231);
    userInfos.save(userInfo2);
    User user3 = new User("tester3", "blabla3@hotmail.com", encoder.encode("password1"));
    users.save(user3);
    UserInfo userInfo3 = new UserInfo(user3.getId(), user3.getUsername(), "SINGAPORE01234567", 87231231);
    userInfos.save(userInfo3);

    ProductInterest interest1 = new ProductInterest(user1, product);
    ProductInterest interest2 = new ProductInterest(user2, product);
    ProductInterest interest3 = new ProductInterest(user3, product);

    userService.addProductInterest(interest1);
    userService.addProductInterest(interest2);
    userService.addProductInterest(interest3);

    URI uri = new URI(baseUrl + port + "/api/products/product/interests/" + product.getId());

    ResponseEntity<ProductInterestResponse[]> result = restTemplate.getForEntity(uri, ProductInterestResponse[].class);
    ProductInterestResponse[] respArr = result.getBody();

    assertNotNull(respArr);
    assertEquals(200, result.getStatusCode().value());
    assertEquals(3, respArr.length);
    }
}