package csd.week6.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * We only need this interface declaration
 * Spring will automatically generate an implementation of the repo
 * 
 * 
 */
@Repository
public interface BookRepository extends JpaRepository <Book, Long> {
    // derived query to find books by title
    List<Book> findByTitle(String title);

}
