package csd.app.payload.request;

import javax.validation.constraints.*;

import lombok.*;

@Getter
@Setter
public class MessageRequest {
    @NotBlank (message = "Content should not be empty")
    private String content;

    @NotBlank (message = "Date time not be empty")
    private String dateTime;

    @NotBlank (message = "Sender username should not be empty")
    private String senderUsername;

    @NotBlank (message = "Receiver username should not be empty")
	private String receiverUsername;
    
    @NotBlank (message = "Chat ID should not be empty")
    private Long chatId;
    
}
