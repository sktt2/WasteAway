package csd.app.chat;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csd.app.product.*;
import csd.app.user.*;

@Service
public class ChatServiceImpl implements ChatService {
    
    @Autowired
    private ChatRepository chats;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private MessageRepository messages;

    public Chat getChatByProductIdAndTakerId(Long productId, Long takerId) {
        Product product = productService.getProduct(productId);
        User taker = userService.getUser(takerId);
        return chats.findByProductAndTaker(product, taker);
    }

    public List<Chat> getChatbyUsername(String username) {
        User user = userService.getUserByUsername(username);
        return chats.findByOwnerOrTaker(user, user);
    }

    public Chat getChatById(Long id) {
        return chats.findById(id).get();
    }

    public List<Message> getMessagesByChat(Chat chat) {
        return messages.findByChat(chat);
    }

    public Chat addChat(Chat chat, Long takerId, Long ownerId, Long productId) {
        if (getChatByProductIdAndTakerId(productId, takerId) != null) {
            return getChatByProductIdAndTakerId(productId, takerId);
        }
        // Separate this into another method
        if (ownerId == takerId) {
            return null;
        }
        chat.setTaker(userService.getUser(takerId));
        chat.setOwner(userService.getUser(ownerId));
        chat.setProduct(productService.getProduct(productId));
        return chats.save(chat);
    }

    public Message addMessage(Message message, String senderUsername, String receiverUsername, Long chatId) {
        message.setSender(userService.getUserByUsername(senderUsername));
        message.setReceiver(userService.getUserByUsername(receiverUsername));
        message.setChat(getChatById(chatId));
        return messages.save(message);
    }
}
