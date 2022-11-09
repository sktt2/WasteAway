package csd.app.chat;

import java.util.*;

public interface ChatService {
    Chat getChatByProductIdAndTakerId(Long productId, Long takerId);
    List<Chat> getChatbyUsername(String username);
    Chat getChatById(Long id);
    List<Message> getMessagesByChat(Chat chat);
    Chat addChat(Long takerId, Long ownerId, Long productId);
    Message addMessage(String content, String dateTime, String senderUsername, String receiverUsername, Long chatId);
}
