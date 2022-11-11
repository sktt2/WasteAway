package csd.chat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import csd.app.product.Product;
import csd.app.product.ProductRepository;
import csd.app.user.User;
import csd.app.user.UserRepository;
import csd.app.chat.Chat;
import csd.app.chat.Message;
import csd.app.chat.ChatRepository;
import csd.app.chat.MessageRepository;

/** Start an actual HTTP server listening at a random port */
@SpringBootTest(classes = csd.app.Main.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ChatIntegrationTest {
    
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
    private ChatRepository chats;

    @Autowired
    private UserRepository users;

    @Autowired
    private ProductRepository products;

    @Autowired
    private MessageRepository messages;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PasswordEncoder encoder;

    @AfterEach
    void tearDown() {
        // clear the database after each test
        messages.deleteAll();
        chats.deleteAll();
        products.deleteAll();
        users.deleteAll();
    }

    @Test
    public void getChatByUsername_Success() throws Exception {
        User owner = new User("owner1", "owner1@test.com", encoder.encode("password1"));
        users.save(owner);
        User taker = new User("taker1", "taker1@test.com", encoder.encode("password1"));
        users.save(taker);
        Product product = new Product("PHONE", "NEW", LocalDateTime.now().toString(),
                "ELECTRONICS", "This is the description");
        product.setImageUrl("https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        product.setUser(owner);
        products.save(product);
        Chat chat = new Chat(taker, owner, product);
        chats.save(chat);
        URI uri = new URI(baseUrl + port + "/api/chat?username=" + owner.getUsername());

        ResponseEntity<String> result = restTemplate.getForEntity(uri,
        String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertNotNull(root);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(1, root.size());
    }

    @Test
    public void getChatByUsername_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/chat?username=usernamedoesnotexist");

        ResponseEntity<String> result = restTemplate.getForEntity(uri,
        String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(400, result.getStatusCode().value());
        assertEquals("User does not exist.", root.path("message").asText());
    }

    @Test
    public void getChatById_Success() throws Exception {
        User owner = new User("owner1", "owner1@test.com", encoder.encode("password1"));
        users.save(owner);
        User taker = new User("taker1", "taker1@test.com", encoder.encode("password1"));
        users.save(taker);
        Product product = new Product("PHONE", "NEW", LocalDateTime.now().toString(),
                "ELECTRONICS", "This is the description");
        product.setImageUrl("https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        product.setUser(owner);
        products.save(product);
        Chat chat = new Chat(taker, owner, product);
        chats.save(chat);
        URI uri = new URI(baseUrl + port + "/api/chat/" + chat.getId());

        ResponseEntity<String> result = restTemplate.getForEntity(uri,
        String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertNotNull(root);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(product.getProductName(), root.path("productName").asText());
    }

    @Test
    public void getChatById_Failure() throws Exception {
        Long id = 9999L;
        URI uri = new URI(baseUrl + port + "/api/chat/" + id);

        ResponseEntity<String> result = restTemplate.getForEntity(uri,
        String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Chat " + id + " not found.", root.path("message").asText());
    }

    @Test
    public void getMessagesByChat_Success() throws Exception {
        User owner = new User("owner1", "owner1@test.com", encoder.encode("password1"));
        users.save(owner);
        User taker = new User("taker1", "taker1@test.com", encoder.encode("password1"));
        users.save(taker);
        Product product = new Product("PHONE", "NEW", LocalDateTime.now().toString(),
                "ELECTRONICS", "This is the description");
        product.setImageUrl("https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        product.setUser(owner);
        products.save(product);
        Chat chat = new Chat(taker, owner, product);
        chats.save(chat);
        Message message1 = new Message("amazing", LocalDateTime.now().toString(), chat, owner, taker);
        messages.save(message1);
        Message message2 = new Message("sadly", LocalDateTime.now().toString(), chat, taker, owner);
        messages.save(message2);
        URI uri = new URI(baseUrl + port + "/api/chat/" + chat.getId() + "/messages");

        ResponseEntity<String> result = restTemplate.getForEntity(uri,
        String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertNotNull(root);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(2, root.size());
    }

    @Test
    public void getMessagesByChat_Failure() throws Exception {
        Long id = 9999L;
        URI uri = new URI(baseUrl + port + "/api/chat/" + id + "/messages");

        ResponseEntity<String> result = restTemplate.getForEntity(uri,
        String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Chat " + id + " not found.", root.path("message").asText());
    }

    @Test
    public void createChat_Success() throws Exception {
        User owner = new User("owner1", "owner1@test.com", encoder.encode("password1"));
        users.save(owner);
        User taker = new User("taker1", "taker1@test.com", encoder.encode("password1"));
        users.save(taker);
        Product product = new Product("PHONE", "NEW", LocalDateTime.now().toString(),
                "ELECTRONICS", "This is the description");
        product.setImageUrl("https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        product.setUser(owner);
        products.save(product);
        URI uri = new URI(baseUrl + port + "/api/chat/");

        JSONObject chatJsonObject = new JSONObject();
        chatJsonObject.put("takerId", taker.getId());
        chatJsonObject.put("ownerId", owner.getId());
        chatJsonObject.put("productId", product.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(chatJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request,
                String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertNotNull(root);
        assertEquals(201, result.getStatusCode().value());
        assertEquals(product.getProductName(), root.path("productName").asText());
    }

    @Test
    public void createChat_Failure() throws Exception {
        User owner = new User("owner1", "owner1@test.com", encoder.encode("password1"));
        users.save(owner);
        Product product = new Product("PHONE", "NEW", LocalDateTime.now().toString(),
                "ELECTRONICS", "This is the description");
        product.setImageUrl("https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        product.setUser(owner);
        products.save(product);
        URI uri = new URI(baseUrl + port + "/api/chat/");

        JSONObject chatJsonObject = new JSONObject();
        chatJsonObject.put("takerId", owner.getId());
        chatJsonObject.put("ownerId", owner.getId());
        chatJsonObject.put("productId", product.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(chatJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request,
                String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Owner cannot be taker.", root.path("message").asText());
    }

    @Test
    public void addMessage_Success() throws Exception {
        User owner = new User("owner1", "owner1@test.com", encoder.encode("password1"));
        users.save(owner);
        User taker = new User("taker1", "taker1@test.com", encoder.encode("password1"));
        users.save(taker);
        Product product = new Product("PHONE", "NEW", LocalDateTime.now().toString(),
                "ELECTRONICS", "This is the description");
        product.setImageUrl("https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        product.setUser(owner);
        products.save(product);
        Chat chat = new Chat(taker, owner, product);
        chats.save(chat);
        URI uri = new URI(baseUrl + port + "/api/chat/" + chat.getId() + "/messages");

        JSONObject chatJsonObject = new JSONObject();
        chatJsonObject.put("content", "amazinger");
        chatJsonObject.put("dateTime", LocalDateTime.now().toString());
        chatJsonObject.put("senderUsername", owner.getUsername());
        chatJsonObject.put("receiverUsername", taker.getUsername());
        chatJsonObject.put("chatId", chat.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(chatJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request,
                String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertNotNull(root);
        assertEquals(201, result.getStatusCode().value());
        assertEquals(owner.getUsername(), root.path("senderUsername").asText());
    }

    @Test
    public void addMessage_Failure() throws Exception {
        User owner = new User("owner1", "owner1@test.com", encoder.encode("password1"));
        users.save(owner);
        User taker = new User("taker1", "taker1@test.com", encoder.encode("password1"));
        users.save(taker);
        Product product = new Product("PHONE", "NEW", LocalDateTime.now().toString(),
                "ELECTRONICS", "This is the description");
        product.setImageUrl("https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
        product.setUser(owner);
        products.save(product);
        Chat chat = new Chat(taker, owner, product);
        chats.save(chat);
        URI uri = new URI(baseUrl + port + "/api/chat/" + chat.getId() + "/messages");

        JSONObject chatJsonObject = new JSONObject();
        chatJsonObject.put("content", "amazinger");
        chatJsonObject.put("dateTime", LocalDateTime.now().toString());
        chatJsonObject.put("senderUsername", owner.getUsername());
        chatJsonObject.put("receiverUsername", owner.getUsername());
        chatJsonObject.put("chatId", chat.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(chatJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request,
                String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertNotNull(root);
        assertEquals(400, result.getStatusCode().value());
        assertEquals("Sender cannot be receiver.", root.path("message").asText());
    }
}
