package csd.app.payload.request;

import java.util.Set;
import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter

public class SignupRequest {
    @NotBlank(message = "Username should not be empty")
    @Size(min = 3, max = 20, message = "Username should be between 3 to 20 characters")
    private String username;

    @NotBlank(message="Email should not be empty")
    @Size(max = 50, message="Email should not be above 50 characters")
    @Email(message = "Invalid Email")
    private String email;

    private Set<String> role;

    @NotBlank(message = "Password should not be empty")
    //pattern checks for both length of the string to be within 8-40 char long, and checks there is at least 1 num and 1 letter.
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password should be at least 8 characters long and should contain at least 1 number and 1 letter")
    private String password;

    @NotBlank(message = "Name should not be empty")
    @Size(min = 3, max = 50, message = "Name should be between 3 to 50 characters long")
    private String name;

    @NotBlank(message = "Address should not be empty")
    @Size(min = 3, max = 50, message = "Address should be between 3 to 50 characters long")
    private String address;

    @NotNull(message = "Phone number should not be empty")
    @Min(value = 80000000, message = "Invalid phone number, phone number should be 8 digits starting with 8/9")
    @Max(value = 99999999, message = "Invalid phone number, phone number should be 8 digits starting with 8/9")
    private int phoneNumber;
}
