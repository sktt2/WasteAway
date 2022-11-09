package csd.app.payload.request;

import javax.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter


public class LoginRequest {
    @NotBlank (message = "Username should not be empty")
    private String username;

    @NotBlank (message = "Password should not be empty")
    private String password;
}
