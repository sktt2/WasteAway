package csd.app.chat;

import java.util.*;

public interface ChatService {
    Chat getChat(Long productId, Long takerId);
    List<Message> getMessagesByChat(Chat chat);
    Chat addChat(Chat chat, Long takerId, Long productId);
}
