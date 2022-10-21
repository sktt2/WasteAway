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

    public Chat getChat(Long productId, Long takerId) {
        Product product = productService.getProduct(productId);
        User taker = userService.getUser(takerId);
        return chats.findByProductAndTaker(product, taker);
    }

    public List<Message> getMessagesByChat(Chat chat) {
        return messages.findByChat(chat);
    }

    public Chat addChat(Chat chat, Long takerId, Long productId) {
        if (getChat(productId, takerId) != null) {
            return chat;
        }
        // Separate this into another method
        if (productService.getProduct(productId).getUser().getId() == takerId) {
            return null;
        }
        chat.setTaker(userService.getUser(takerId));
        chat.setProduct(productService.getProduct(productId));
        return chats.save(chat);
    }
}
