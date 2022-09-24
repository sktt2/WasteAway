package csd.week6.review;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import csd.week6.book.BookNotFoundException;
import csd.week6.book.BookRepository;

//TODO: Enable the cross origin communication if required

@RestController
public class ReviewController {
    private ReviewRepository reviews;
    private BookRepository books;

    public ReviewController(ReviewRepository reviews, BookRepository books){
        this.reviews = reviews;
        this.books = books;
    }

    @GetMapping("/books/{bookId}/reviews")
    public List<Review> getAllReviewsByBookId(@PathVariable (value = "bookId") Long bookId) {
        if(!books.existsById(bookId)) {
            throw new BookNotFoundException(bookId);
        }
        return reviews.findByBookId(bookId);
    }

    @PostMapping("/books/{bookId}/reviews")
    public Review addReview(@PathVariable (value = "bookId") Long bookId, @Valid @RequestBody Review review) {
        // using "map" to handle the returned Optional object from "findById(bookId)"
        return books.findById(bookId).map(book ->{
            review.setBook(book);
            return reviews.save(review);
        }).orElseThrow(() -> new BookNotFoundException(bookId));
    }

    @PutMapping("/books/{bookId}/reviews/{reviewId}")
    public Review updateReview(@PathVariable (value = "bookId") Long bookId,
                                 @PathVariable (value = "reviewId") Long reviewId,
                                 @Valid @RequestBody Review newReview) {
        if(!books.existsById(bookId)) {
            throw new BookNotFoundException(bookId);
        }
        return reviews.findByIdAndBookId(reviewId, bookId).map(review -> {
            review.setReview(newReview.getReview());
            return reviews.save(review);
        }).orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    /**
     * Use a ResponseEntity to have more control over the response sent to client
     */
    @DeleteMapping("/books/{bookId}/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable (value = "bookId") Long bookId,
                              @PathVariable (value = "reviewId") Long reviewId) {
        
        if(!books.existsById(bookId)) {
            throw new BookNotFoundException(bookId);
        }

        return reviews.findByIdAndBookId(reviewId, bookId).map(review -> {
            reviews.delete(review);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }
}