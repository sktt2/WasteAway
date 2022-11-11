package csd.app.chat;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import csd.app.product.*;
import csd.app.user.*;

@Service
public class ChatServiceImpl implements ChatService {
    
    private ChatRepository chats;
    private UserService userService;
    private ProductService productService;
    private MessageRepository messages;

    @Autowired
    public ChatServiceImpl(ChatRepository chats, UserService userService,
            ProductService productService, MessageRepository messages) {
        this.chats = chats;
        this.userService = userService;
        this.productService = productService;
        this.messages = messages;
    }

    public Chat getChatByProductIdAndTakerId(Long productId, Long takerId) {
        Product product = productService.getProduct(productId);
        User taker = userService.getUser(takerId);
        return chats.findByProductAndTaker(product, taker);
    }

    public List<Chat> getChatByUsername(String username) {
        User user = userService.getUserByUsername(username);
        List<Chat> chatOwner = chats.findByOwner(user);
        List<Chat> chatTaker = chats.findByTaker(user);
        chatOwner.addAll(chatTaker);
        return chatOwner;
    }

    public Chat getChatById(Long id) {
        return chats.findById(id)
                .orElseThrow(() -> new RuntimeException("Chat " + id + " not found."));
    }

    public List<Message> getMessagesByChat(Chat chat) {

        // Throws illegalargumentexception if chat is null
        return messages.findByChat(chat);
    }

    public Chat addChat(Long takerId, Long ownerId, Long productId) {

        // Return chat if it exists already
        if (getChatByProductIdAndTakerId(productId, takerId) != null) {
            return getChatByProductIdAndTakerId(productId, takerId);
        }

        // Caught by RestExceptionHandler for badRequest()
        if (ownerId == takerId) {
            throw new RuntimeException("Owner cannot be taker.");
        }

        User taker = userService.getUser(takerId);
        User owner = userService.getUser(ownerId);
        Product product = productService.getProduct(productId);
        Chat chat = new Chat(taker, owner, product);
        return chats.save(chat);
    }

    public Message addMessage(String content, String dateTime, String senderUsername, String receiverUsername, Long chatId) {
        if (senderUsername.equals(receiverUsername)) {
            throw new RuntimeException("Sender cannot be receiver.");
        }
        User sender = userService.getUserByUsername(senderUsername);
        User receiver = userService.getUserByUsername(receiverUsername);
        Chat chat = getChatById(chatId);
        Message message = new Message(content, dateTime, chat, sender, receiver);
        return messages.save(message);
    }
}
