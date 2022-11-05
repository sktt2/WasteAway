package csd.app.payload.request;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter

public class ChangePasswordRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotBlank
    @Size(min = 8, max = 40)
    private String currentPassword;

    @NotBlank
    @Size(min = 8, max = 40)
    private String newPassword;
}
