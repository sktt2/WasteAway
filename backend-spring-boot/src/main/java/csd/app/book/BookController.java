package csd.app.book;

import java.util.List;

import javax.validation.Valid;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//to permit cross origin communication
@CrossOrigin(origins = "http://localhost:3000")

@RestController
public class BookController {
    private BookService bookService;

    public BookController(BookService bs) {
        this.bookService = bs;
    }

    /**
     * List all books in the system
     * 
     * @return list of all books
     */
    @GetMapping("/books")
    public List<Book> getBooks() {
        return bookService.listBooks();
    }

    /**
     * Search for book with the given id
     * If there is no book with the given "id", throw a BookNotFoundException
     * 
     * @param id
     * @return book with the given id
     */
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Long id) {
        Book book = bookService.getBook(id);

        // Need to handle "book not found" error using proper HTTP status code
        // In this case it should be HTTP 404
        if (book == null)
            throw new BookNotFoundException(id);
        return bookService.getBook(id);

    }

    /**
     * Add a new book with POST request to "/books"
     * Note the use of @RequestBody
     * 
     * @param book
     * @return the newly added book
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/books")
    public Book addBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.addBook(book);
        if (savedBook == null)
            throw new BookExistsException(book.getTitle());
        return savedBook;
    }

    /**
     * If there is no book with the given "id", throw a BookNotFoundException
     * 
     * @param id
     * @param newBookInfo
     * @return the updated, or newly added book
     */
    @PutMapping("/books/{id}")
    public Book updateBook(@PathVariable Long id, @Valid @RequestBody Book newBookInfo) {
        Book book = bookService.updateBook(id, newBookInfo);
        if (book == null)
            throw new BookNotFoundException(id);

        return book;
    }

    /**
     * Remove a book with the DELETE request to "/books/{id}"
     * If there is no book with the given "id", throw a BookNotFoundException
     * 
     * @param id
     */
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BookNotFoundException(id);
        }
    }
}