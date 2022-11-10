package csd.notification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.time.LocalDateTime;
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

import csd.app.product.Product;
import csd.app.product.ProductRepository;
import csd.app.user.User;
import csd.app.user.UserRepository;
import csd.app.user.UserInfo;
import csd.app.user.UserInfoRepository;
import csd.app.user.UserRecommendation;
import csd.app.user.UserRecommendationRepository;
import csd.app.chat.Chat;
import csd.app.chat.ChatRepository;
import csd.app.chat.MessageRepository;
import csd.app.notification.Notification;
import csd.app.notification.NotificationRepository;
import csd.app.payload.response.NotificationResponse;

/** Start an actual HTTP server listening at a random port */
@SpringBootTest(classes = csd.app.Main.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class NotificationIntegrationTest {

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
        private BCryptPasswordEncoder encoder;

        @Autowired
        private ChatRepository chats;

        @Autowired
        private MessageRepository messages;

        @Autowired
        private NotificationRepository notifications;

        @Autowired
        private final ObjectMapper objectMapper = new ObjectMapper();

        @AfterEach
        void tearDown() {
                // clear the database after each test
                products.deleteAll();
                users.deleteAll();
                userInfos.deleteAll();
        }

        @Test
        public void getNotification_InvalidUsername_Failure() throws Exception {
                URI uri = new URI(baseUrl + port + "/api/notifications/invaliduser");

                ResponseEntity<String> result = restTemplate.getForEntity(uri,
                                String.class);
                JsonNode root = objectMapper.readTree(result.getBody());

                assertEquals(400, result.getStatusCode().value());
                assertEquals("User does not exist", root.path("message").asText());
        }

        @Test
        public void getNotificationByUsername_ValidUsername_Success() throws Exception {
                // arrange owner
                User user = new User("tester2", "blabla@hotmail.com",
                                encoder.encode("password1"));
                users.save(user);
                UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(),
                                "SINGAPORE1234567", 87231231);
                userInfos.save(userInfo);

                // arrange product
                Product product = new Product("PHONE", "NEW", LocalDateTime.now().toString(),
                                "ELECTRONICS", "this is a description");
                product.setImageUrl(
                                "https://firebasestorage.googleapis.com/v0/b/wasteaway-d8e06.appspot.com/o/2%2F54703383-060f-4eb2-a666-5edee035e9ba?alt=media&token=a0fa175d-c74f-48cf-9076-82984008a24b");
                product.setUser(user);
                products.save(product);

                // arrange taker
                User user2 = new User("tester23", "blabla2@hotmail.com",
                                "password");
                users.save(user2);
                UserInfo userInfo2 = new UserInfo(user2.getId(), user2.getUsername(),
                                "SINGAPORE1234568", 87231234);
                users.save(user2);
                userInfos.save(userInfo2);
                Chat validChat = new Chat();

                validChat.setTaker(user2);
                validChat.setOwner(user);
                validChat.setProduct(product);

                chats.save(validChat);

                Notification notif = new Notification(validChat, user2, user, false);
                notifications.save(notif);

                String username = user.getUsername();
                URI uri = new URI(baseUrl + port + "/api/notifications/" + username);

                ResponseEntity<NotificationResponse[]> result = restTemplate.getForEntity(uri,
                                NotificationResponse[].class);
                NotificationResponse[] notifArr = result.getBody();

                assertNotNull(notifArr);
                assertEquals(200, result.getStatusCode().value());
                assertEquals(1, notifArr.length);
        }
}
