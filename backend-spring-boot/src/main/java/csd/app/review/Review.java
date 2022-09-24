package csd.week6.review;

import javax.persistence.*;
import javax.validation.constraints.*;

import csd.week6.book.Book;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Review {
    private  @Id @GeneratedValue (strategy = GenerationType.IDENTITY) Long id;
    @NotNull(message = "Book review should not be null")
    // null elements are considered valid, so we need a size constraints too
    @Size(min = 10, message = "Book review should be at least 10 characters long")
    private String review;

    @ManyToOne
    // the column "book_id" will be in the auto-generated table "review"
    // nullable = false: add not-null constraint to the database column "book_id"
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;
}