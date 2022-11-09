package csd.app.payload.request;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter

@ToString
public class UpdateUserRequest {
	@NotBlank(message = "Username should not be empty")
	@Size(min = 3, max = 20)
	private String username;

	@NotBlank(message = "Email not be empty")
	@Size(max = 50, message = "Email should be under 50 characters")
	@Email(message = "Invalid email")
	private String email;

	@NotBlank(message = "Name not be empty")
	@Size(min = 3, max = 50, message = "Name should be between 3 to 50 characters")
	private String name;

	@NotBlank(message = "Address not be empty")
	@Size(min = 3, max = 50, message = "Address should be between 3 to 50 characters")
	private String address;

	@NotNull(message = "Phone number not be null")
	@Pattern(regexp = "^[89][0-9]{7}", message = "Invalid phone number")
	private int phoneNumber;
}