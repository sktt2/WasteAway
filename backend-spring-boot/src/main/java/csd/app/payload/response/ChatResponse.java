package csd.app.payload.response;

import lombok.*;

@Getter
@Setter
public class ChatResponse {
    private Long id;

    public ChatResponse(Long id) {
        this.id = id;
    }
}
