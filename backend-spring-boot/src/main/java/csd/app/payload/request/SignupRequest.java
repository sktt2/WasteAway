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
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 8, max = 40, message = "Password should be at least 8 characters with at least 1 number and 1 letter")
    private String password;

    @NotBlank
    @Size(min = 3, max = 50, message = "Name should be between 3 to 50 characters long")
    private String name;

    @Size(min = 3, max = 50, message = "Address should be between 3 to 50 characters long")
    private String address;

    @NotNull(message = "Phone number should not be empty")
    private int phoneNumber;
}
