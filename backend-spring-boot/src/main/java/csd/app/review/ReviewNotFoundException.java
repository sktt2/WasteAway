package csd.week6.review;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // 404 Error
public class ReviewNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ReviewNotFoundException(Long id) {
        super("Could not find review " + id);
    }
    
}
