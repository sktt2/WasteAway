package csd.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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

import csd.app.user.*;
import csd.app.roles.*;

/** Start an actual HTTP server listening at a random port */
@SpringBootTest(classes = csd.Main.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthIntegrationTest {

    private int port = 8443;

    private final String baseUrl = "https://wasteaway.gay:";

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
    private RoleRepository roles;

    @Autowired
    private UserRepository users;

    @Autowired
    private UserInfoRepository userInfos;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PasswordEncoder encoder;

    private static boolean initialized = false;

    @BeforeEach
    void init() {
        if (!initialized) {
            Optional<Role> roleAdmin = roles.findByName(ERole.ROLE_ADMIN);
            Optional<Role> roleUser = roles.findByName(ERole.ROLE_USER);
            if (!roleAdmin.isPresent()) {
                roles.save(new Role(ERole.ROLE_ADMIN));
            }
            if (!roleUser.isPresent()) {
                roles.save(new Role(ERole.ROLE_USER));
            }
        }
        initialized = true;
    }

    @AfterEach
    void tearDown() {
        // clear the database after each test
        users.deleteAll();
        userInfos.deleteAll();
    }

    @Test
    public void registerUser_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/auth/signup");
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("username", "tester1");
        personJsonObject.put("email", "tester1@email.com");
        personJsonObject.put("password", "password");
        personJsonObject.put("name", "Tester 1");
        personJsonObject.put("address", "TesterVille Street 1");
        personJsonObject.put("phoneNumber", "81112111");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        // assertEquals(200, result.getStatusCode().value());
        assertEquals("User registered successfully!", root.path("message").asText());
        assertEquals(true, users.existsByUsername("tester1"));
        assertEquals(200, result.getStatusCode().value());
    }

    @Test
    public void registerUser_InvalidUsername_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/auth/signup");
        User user = new User("tester8", "tester8@email.com", encoder.encode("password"));
        users.save(user);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("username", "tester8");
        personJsonObject.put("email", "tester9@email.com");
        personJsonObject.put("password", "password");
        personJsonObject.put("name", "Tester 9");
        personJsonObject.put("address", "TesterVille Street 9");
        personJsonObject.put("phoneNumber", "89992999");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Error: Username is already taken!", root.path("message").asText());
        assertEquals(false, users.existsByEmail("tester9@email.com"));
    }

    @Test
    public void registerUser_InvalidEmail_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/auth/signup");
        User user = new User("tester10", "tester10@email.com", encoder.encode("password"));
        users.save(user);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("username", "tester6");
        personJsonObject.put("email", "tester10@email.com");
        personJsonObject.put("password", "password");
        personJsonObject.put("name", "Tester 10");
        personJsonObject.put("address", "TesterVille Street 10");
        personJsonObject.put("phoneNumber", "81112111");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Error: Email is already in use!", root.path("message").asText());
        assertEquals(false, users.existsByUsername("tester1"));
    }

    @Test
    public void authenticateUser_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/auth/signin");
        User user = new User("tester2", "tester2@email.com", encoder.encode("password"));
        users.save(user);
        UserInfo userInfo = new UserInfo(user.getId(), "Tester 2", "TesterVille Street 2", 81112111);
        userInfos.save(userInfo);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("username", "tester2");
        personJsonObject.put("password", "password");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(200, result.getStatusCode().value());
        assertEquals(user.getUsername(), root.path("username").asText());
    }

    @Test
    public void authenticateUser_InvalidUsername_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/auth/signin");
        User user = new User("tester3", "tester3@email.com", encoder.encode("password"));
        users.save(user);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("username", "tester1");
        personJsonObject.put("password", "password");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Bad credentials", root.path("message").asText());
    }

    @Test
    public void authenticateUser_InvalidPassword_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/auth/signin");
        User user = new User("tester4", "tester4@email.com", encoder.encode("password"));
        users.save(user);
        JSONObject personJsonObject = new JSONObject();
        personJsonObject.put("username", "tester4");
        personJsonObject.put("password", "wrongpassword");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Bad credentials", root.path("message").asText());
    }

    @Test
    public void logoutUser_Success() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/auth/signout");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(new JSONObject().toString(), headers);

        ResponseEntity<String> result = restTemplate.postForEntity(uri, request, String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(200, result.getStatusCode().value());
        assertEquals("You've been signed out!", root.path("message").asText());
    }
}
