package csd.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductNotFoundException extends RuntimeException{
    
    public ProductNotFoundException(Long id){
        super("Product not found: " + id);
    }
}