package csd.app.payload.request;

import java.util.Set;
import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter

public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @Size(min = 3, max = 50)
    private String address;

    private int phoneNumber;
}
