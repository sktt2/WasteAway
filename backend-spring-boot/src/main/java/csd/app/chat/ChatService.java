package csd.app.chat;

import java.util.*;

public interface ChatService {
    Chat getChatByProductIdAndTakerId(Long productId, Long takerId);
    List<Chat> getChatbyUsername(String username);
    Chat getChatById(Long id);
    List<Message> getMessagesByChat(Chat chat);
    Chat addChat(Chat chat, Long takerId, Long ownerId, Long productId);
    Message addMessage(Message message, String senderUsername, String receiverUsername, Long chatId);
}
