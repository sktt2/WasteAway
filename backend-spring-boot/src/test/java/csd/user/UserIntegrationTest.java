package csd.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.net.URI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

import csd.app.user.User;
import csd.app.user.UserRepository;
import csd.app.user.UserInfo;
import csd.app.user.UserInfoRepository;
import csd.app.user.UserRecommendation;
import csd.app.user.UserRecommendationRepository;
import csd.app.payload.request.RecommendationRequest;

/** Start an actual HTTP server listening at a random port */
@SpringBootTest(classes = csd.app.Main.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class UserIntegrationTest {

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
    private UserRepository users;

    @Autowired
    private UserInfoRepository userInfos;

    @Autowired
    private UserRecommendationRepository userRecommendations;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();

    @AfterEach
    void tearDown() {
        // clear the database after each test
        users.deleteAll();
        userInfos.deleteAll();
        userRecommendations.deleteAll();
    }

    @Test
    public void updateUserRecommendation_ValidRecommendation_Success() throws Exception {
        User user = new User("tester2", "blabla@hotmail.com",
                encoder.encode("password1"));
        users.save(user);
        Long userId = user.getId();
        UserInfo userInfo = new UserInfo(userId, user.getUsername(),
                "SINGAPORE01234567", 87231231);
        userInfos.save(userInfo);
        UserRecommendation userRecommendation = new UserRecommendation("NONE", userId);
        userRecommendations.save(userRecommendation);
        RecommendationRequest recommendationRequest = new RecommendationRequest("ELECTRONICS", user.getUsername());

        URI uri = new URI(baseUrl + port + "/api/products/recommendation/update");

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT,
                new HttpEntity<RecommendationRequest>(recommendationRequest), String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        UserRecommendation updatedUserRecommendation = userRecommendations.findById(userId).get();
        assertEquals(200, result.getStatusCode().value());
        assertEquals("User recommendation updated successfully", root.path("message").asText());
        assertEquals(recommendationRequest.getRecommendation(), updatedUserRecommendation.getRecommendation());
    }

    @Test
    public void updateUserRecommendation_InvalidRecommendation_Failure() throws Exception {
        User user = new User("tester2", "blabla@hotmail.com",
                encoder.encode("password1"));
        users.save(user);
        Long userId = user.getId();
        UserInfo userInfo = new UserInfo(userId, user.getUsername(),
                "SINGAPORE01234567", 87231231);
        userInfos.save(userInfo);
        UserRecommendation userRecommendation = new UserRecommendation("NONE", userId);
        userRecommendations.save(userRecommendation);
        RecommendationRequest recommendationRequest = new RecommendationRequest("", user.getUsername());

        URI uri = new URI(baseUrl + port + "/api/products/recommendation/update");

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT,
                new HttpEntity<RecommendationRequest>(recommendationRequest), String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Recommendation cannot be empty", root.path("message").asText());
    }

    @Test
    public void updateUserRecommendation_InvalidUsername_Failure() throws Exception {
        User user = new User("tester2", "blabla@hotmail.com",
                encoder.encode("password1"));
        users.save(user);
        Long userId = user.getId();
        UserInfo userInfo = new UserInfo(userId, user.getUsername(),
                "SINGAPORE01234567", 87231231);
        userInfos.save(userInfo);
        UserRecommendation userRecommendation = new UserRecommendation("NONE", userId);
        userRecommendations.save(userRecommendation);        
        RecommendationRequest recommendationRequest = new RecommendationRequest("NONE", "");

        URI uri = new URI(baseUrl + port + "/api/products/recommendation/update");

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT,
                new HttpEntity<RecommendationRequest>(recommendationRequest), String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Username cannot be empty", root.path("message").asText());
    }

    @Test
    public void updateUserRecommendation_InvalidFields_Failure() throws Exception {        
        User user = new User("tester2", "blabla@hotmail.com",
                encoder.encode("password1"));
        users.save(user);
        Long userId = user.getId();
        UserInfo userInfo = new UserInfo(userId, user.getUsername(),
                "SINGAPORE01234567", 87231231);
        userInfos.save(userInfo);
        UserRecommendation userRecommendation = new UserRecommendation("NONE", userId);
        userRecommendations.save(userRecommendation); 
        RecommendationRequest recommendationRequest = new RecommendationRequest("", "");

        URI uri = new URI(baseUrl + port + "/api/products/recommendation/update");

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.PUT,
                new HttpEntity<RecommendationRequest>(recommendationRequest), String.class);
        JsonNode root = objectMapper.readTree(result.getBody());

        System.out.println("***********");
        System.out.println(root);
        System.out.println(result);
        assertEquals(400, result.getStatusCode().value());
        assertEquals("Recommendation cannot be emptyUsername cannot be empty", root.path("message").asText());
    }

    @Test
    public void getUserRecommendation_Success() throws Exception {
        User user = new User("tester2", "blabla@hotmail.com",
                encoder.encode("password1"));
        users.save(user);
        Long userId = user.getId();
        UserInfo userInfo = new UserInfo(userId, user.getUsername(),
                "SINGAPORE01234567", 87231231);
        userInfos.save(userInfo);
        UserRecommendation userRecommendation = new UserRecommendation("ELECTRONICS", userId);
        userRecommendations.save(userRecommendation);

        URI uri = new URI(baseUrl + port + "/api/products/recommendation/" + userId);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        String validRecommendation = result.getBody();
        assertNotNull(validRecommendation);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(userRecommendation.getRecommendation(), validRecommendation);
    }

    @Test
    public void getUserRecommendation_InvalidUsername_Failure() throws Exception {
        URI uri = new URI(baseUrl + port + "/api/products/recommendation/4");

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        String validRecommendation = result.getBody();

        assertNotEquals(validRecommendation, "");
        assertEquals(400, result.getStatusCode().value(), "Username cannot be empty");
    }
}
