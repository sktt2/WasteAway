package csd.week6.book;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookExistsException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public BookExistsException(String title) {
        super("This title exists: " + title);
    }
    
}
