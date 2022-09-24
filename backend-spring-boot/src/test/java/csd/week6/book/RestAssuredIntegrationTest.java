// package csd.app.book;

// import static io.restassured.RestAssured.given;
// import static io.restassured.config.RedirectConfig.redirectConfig;
// import static org.hamcrest.Matchers.equalTo;

// import java.net.URI;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// import csd.app.user.User;
// import csd.app.user.UserRepository;
// import io.restassured.RestAssured;
// import io.restassured.config.JsonConfig;
// import io.restassured.path.json.config.JsonPathConfig;
// import net.minidev.json.JSONObject;

// // Using REST Assured https://rest-assured.io/ is another way to write integration tests
// @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
// class RestAssuredIntegrationTest {

//     @LocalServerPort
//     private int port;

//     private final String baseUrl = "http://localhost:";

//     @Autowired
//     private BookRepository books;

//     @Autowired
//     private UserRepository users;

//     @Autowired
//     private BCryptPasswordEncoder encoder;

//     @BeforeAll
//     public static void initClass() {
//         RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
//         RestAssured.useRelaxedHTTPSValidation();
//         RestAssured.urlEncodingEnabled = false;
//         RestAssured.config = RestAssured.config()
//                 .jsonConfig(JsonConfig.jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.DOUBLE))
//                 .redirect(redirectConfig().followRedirects(false));
//     }

//     @AfterEach
//     void tearDown() {
//         // clear the database after each test
//         books.deleteAll();
//         users.deleteAll();
//     }

//     @Test
//     public void getBooks_Success() throws Exception {
//         URI uri = new URI(baseUrl + port + "/books");
//         books.save(new Book("Gone With The Wind"));

//         given().get(uri).then().statusCode(200).body("size()", equalTo(1));
//     }

//     @Test
//     public void getBook_ValidBookId_Success() throws Exception {
//         Book book = new Book("Gone With The Wind");
//         Long id = books.save(book).getId();
//         URI uri = new URI(baseUrl + port + "/books/" + id);

//         given().get(uri).then().statusCode(200).body("id", equalTo(id.intValue()), "title", equalTo(book.getTitle()));

//     }

//     @Test
//     public void addBook_Success() throws Exception {
//         URI uri = new URI(baseUrl + port + "/books");
//         users.save(new User("admin", encoder.encode("goodpassword"), "ROLE_ADMIN"));

//         // create the request body in JSON
//         JSONObject requestParams = new JSONObject();
//         requestParams.put("title", "A New Hope");

//         // issue the post request with basic authentication
//         given().auth().basic("admin", "goodpassword")
//                 .accept("*/*").contentType("application/json")
//                 .body(requestParams.toJSONString()).post(uri).then().statusCode(201)
//                 .body("title", equalTo("A New Hope"));
//     }

// }
