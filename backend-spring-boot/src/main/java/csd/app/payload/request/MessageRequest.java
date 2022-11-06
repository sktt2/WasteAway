package csd.app.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {

    private String content;
    private String dateTime;
    private String senderUsername;
	private String receiverUsername;
    private Long chatId;
    
}
