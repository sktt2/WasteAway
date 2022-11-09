package csd.app.payload.response;

import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.*;

@Getter
public class MessageResponse {

    @NotBlank(message = "Message response cannot be empty or null")
    private String message;

    @Autowired
    public MessageResponse(String message) {
        this.message = message;
    }
}
