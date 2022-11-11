package csd.app.payload.request;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter

@ToString
public class UpdateUserRequest {
	@NotBlank(message = "Username should not be empty")
	@Size(min = 3, max = 20,  message = "Username should be between 3 to 20 characters")
	private String username;

	@NotBlank(message = "Email should not be empty")
	@Size(max = 50, message = "Email should be under 50 characters")
	@Email(message = "Invalid email")
	private String email;

	@NotNull(message = "Name should not be empty")
	@Size(min = 3, max = 50, message = "Name should be between 3 to 50 characters")
	private String name;

	@NotNull(message = "Address should not be empty")
	@Size(min = 3, max = 50, message = "Address should be between 3 to 50 characters")
	private String address;

	@Min(value = 80000000, message = "Invalid phone number, phone number should be 8 digits starting with 8/9")
    @Max(value = 99999999, message = "Invalid phone number, phone number should be 8 digits starting with 8/9")
	private int phoneNumber;
}