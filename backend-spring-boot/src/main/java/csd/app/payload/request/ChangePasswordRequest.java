package csd.app.payload.request;

import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter

public class ChangePasswordRequest {
    @NotBlank(message = "Username should not be empty")
    @Size(min = 3, max = 20, message = "Username should be 3 to 20 characters long")
    private String username;

    @NotBlank(message = "Current password should not be empty")
    @Size(min = 8, max = 40)
    private String currentPassword;

    @NotBlank(message = "New password should not be empty")
    //pattern checks for both length of the string to be within 8-40 char long, and checks there is at least 1 num and 1 letter.
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", message = "Password should be at least 8 characters long and should contain at least 1 number and 1 letter")
    private String newPassword;
}
