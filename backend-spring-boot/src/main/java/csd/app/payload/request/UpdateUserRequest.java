package csd.app.payload.request;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter

@ToString
public class UpdateUserRequest {
	@NotBlank
	@Size(min = 3, max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(min = 3, max = 50)
	private String name;

	@NotBlank
	@Size(min = 3, max = 50)
	private String address;

	@NotBlank
	private int phoneNumber;
}