package csd.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SameUserException extends RuntimeException {
	public SameUserException() {
		super("Cannot give product to own user");
	}
}
