package csd.week6.book;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import csd.week6.user.User;
import csd.week6.user.UserRepository;

/** Start an actual HTTP server listening at a random port*/
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class BookIntegrationTest {

	@LocalServerPort
	private int port;

	private final String baseUrl = "http://localhost:";

	@Autowired
	/**
	 * Use TestRestTemplate for testing a real instance of your application as an external actor.
	 * TestRestTemplate is just a convenient subclass of RestTemplate that is suitable for integration tests.
 	 * It is fault tolerant, and optionally can carry Basic authentication headers.
	 */
	private TestRestTemplate restTemplate;

	@Autowired
	private BookRepository books;

	@Autowired
	private UserRepository users;

	@Autowired
	private BCryptPasswordEncoder encoder;


	@AfterEach
	void tearDown(){
		// clear the database after each test
		books.deleteAll();
		users.deleteAll();
	}

	@Test
	public void getBooks_Success() throws Exception {
		URI uri = new URI(baseUrl + port + "/books");
		books.save(new Book("Gone With The Wind"));
		
		// Need to use array with a ReponseEntity here
		ResponseEntity<Book[]> result = restTemplate.getForEntity(uri, Book[].class);
		Book[] books = result.getBody();
		
		assertEquals(200, result.getStatusCode().value());
		assertEquals(1, books.length);
	}

	@Test
	public void getBook_ValidBookId_Success() throws Exception {
		Book book = new Book("Gone With The Wind");
		Long id = books.save(book).getId();
		URI uri = new URI(baseUrl + port + "/books/" + id);
		
		ResponseEntity<Book> result = restTemplate.getForEntity(uri, Book.class);
			
		assertEquals(200, result.getStatusCode().value());
		assertEquals(book.getTitle(), result.getBody().getTitle());
	}

	@Test
	public void getBook_InvalidBookId_Failure() throws Exception {
		URI uri = new URI(baseUrl + port + "/books/1");
		
		ResponseEntity<Book> result = restTemplate.getForEntity(uri, Book.class);
			
		assertEquals(404, result.getStatusCode().value());
	}

	@Test
	public void addBook_Success() throws Exception {
		URI uri = new URI(baseUrl + port + "/books");
		Book book = new Book("A New Hope");
		users.save(new User("admin", encoder.encode("goodpassword"), "ROLE_ADMIN"));

		ResponseEntity<Book> result = restTemplate.withBasicAuth("admin", "goodpassword")
										.postForEntity(uri, book, Book.class);
			
		assertEquals(201, result.getStatusCode().value());
		assertEquals(book.getTitle(), result.getBody().getTitle());
	}

	/**
	 * TODO: Activity 2 (Week 6)
	 * Add integration tests for delete/update a book.
	 * For delete operation: there should be two tests for success and failure (book not found) scenarios.
	 * Similarly, there should be two tests for update operation.
	 * You should assert both the HTTP response code, and the value returned if any
	 * 
	 * For delete and update, you should use restTemplate.exchange method to send the request
	 * E.g.: ResponseEntity<Void> result = restTemplate.withBasicAuth("admin", "goodpassword")
										.exchange(uri, HttpMethod.DELETE, null, Void.class);
	 */
	// your code here
	
}
