package csd.app.payload.request;

import javax.validation.constraints.*;

import lombok.*;

@Getter
public class MessageRequest {

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 2000, message = "Message content must be 2000 characters or lower.")
    private String content;

    @NotBlank(message = "Date time cannot be empty")
    private String dateTime;

    @NotBlank(message = "Sender username cannot be empty")
    private String senderUsername;

    @NotBlank(message = "Receiver username cannot be empty")
	private String receiverUsername;
    
    @NotNull(message = "Chat Id cannot be null")
    private Long chatId;
    
}
