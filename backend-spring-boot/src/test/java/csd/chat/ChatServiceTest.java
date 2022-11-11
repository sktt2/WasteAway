package csd.chat;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.*;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import csd.app.chat.*;
import csd.app.exception.ProductNotFoundException;
import csd.app.product.*;
import csd.app.user.*;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

    @Mock
    private ChatRepository chats;

    @Mock
    private MessageRepository messages;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;
    
    @InjectMocks
    private ChatServiceImpl chatService;

    @Test
    void getChatById_ValidId_ReturnChat() {
        User owner = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        owner.setId(1L);
        User taker = new User("taker1", "taker1@email.com", "password1");
        taker.setId(2L);
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setId(1L);
        Chat chat = new Chat(taker, owner, product);
        chat.setId(1L);
        when(chats.findById(any(Long.class))).thenReturn(Optional.of(chat));

        Chat savedChat = chatService.getChatById(chat.getId());

        assertNotNull(savedChat);
        assertEquals(chat, savedChat);
        verify(chats).findById(chat.getId());
    }

    @Test
    void getChatById_InvalidId_ThrowsRuntimeException() {
        User owner = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        owner.setId(1L);
        User taker = new User("taker1", "taker1@email.com", "password1");
        taker.setId(2L);
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setId(1L);
        Chat chat = new Chat(taker, owner, product);
        chat.setId(1L);
        when(chats.findById(any(Long.class))).thenThrow(new RuntimeException("Chat 9999 not found."));

        Throwable exception = assertThrows(RuntimeException.class, () -> chatService.getChatById(9999L));
        assertEquals("Chat 9999 not found.", exception.getMessage());
        verify(chats).findById(9999L);
    }

    @Test
    void getChatByProductIdAndTakerId_ValidProductIdAndTakerId_ReturnChat() {
        User owner = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        owner.setId(1L);
        User taker = new User("taker1", "taker1@email.com", "password1");
        taker.setId(2L);
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setId(1L);
        Chat chat = new Chat(taker, owner, product);
        chat.setId(1L);
        when(productService.getProduct(any(Long.class))).thenReturn(product);
        when(userService.getUser(any(Long.class))).thenReturn(taker);
        when(chats.findByProductAndTaker(any(Product.class), any(User.class))).thenReturn(chat);

        Chat savedChat = chatService.getChatByProductIdAndTakerId(product.getId(), taker.getId());

        assertNotNull(savedChat);
        assertEquals(chat, savedChat);
        verify(productService).getProduct(product.getId());
        verify(userService).getUser(taker.getId());
        verify(chats).findByProductAndTaker(product, taker);
    }

    @Test
    void getChatByProductIdAndTakerId_InvalidProductId_ThrowsProductNotFoundException() {
        User owner = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        owner.setId(1L);
        User taker = new User("taker1", "taker1@email.com", "password1");
        taker.setId(2L);
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setId(1L);
        Chat chat = new Chat(taker, owner, product);
        chat.setId(1L);
        when(productService.getProduct(any(Long.class))).thenThrow(new ProductNotFoundException(9999L));

        Throwable exception = assertThrows(ProductNotFoundException.class,
                () -> chatService.getChatByProductIdAndTakerId(9999L, taker.getId()));
        assertEquals("Product not found: 9999", exception.getMessage());
        verify(productService).getProduct(9999L);
    }

    @Test
    void getChatByUsername_ValidUsernameAndHasOnlyOwner_ReturnChats() {
        User owner = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        owner.setId(1L);
        User taker = new User("taker1", "taker1@email.com", "password1");
        taker.setId(2L);
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setId(1L);
        Chat chat = new Chat(taker, owner, product);
        chat.setId(1L);
        List<Chat> chatList = new ArrayList<>();
        chatList.add(chat);
        List<Chat> emptyChatList = new ArrayList<>();
        chatList = Mockito.spy(chatList);
        // Test user is owner
        when(userService.getUserByUsername(any(String.class))).thenReturn(owner);
        when(chats.findByOwner(any(User.class))).thenReturn(chatList);
        when(chats.findByTaker(any(User.class))).thenReturn(emptyChatList);
        Mockito.doReturn(false).when(chatList).addAll(any(ArrayList.class));

        List<Chat> savedChatList = chatService.getChatByUsername(owner.getUsername());

        assertNotNull(savedChatList);
        assertEquals(chatList, savedChatList);
        verify(userService).getUserByUsername(owner.getUsername());
        verify(chats).findByOwner(owner);
        verify(chats).findByTaker(owner);
        verify(chatList).addAll(emptyChatList);
    }

    @Test
    void getChatByUsername_ValidUsernameAndHasOnlyTaker_ReturnChats() {
        User owner = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        owner.setId(1L);
        User taker = new User("taker1", "taker1@email.com", "password1");
        taker.setId(2L);
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setId(1L);
        Chat chat = new Chat(taker, owner, product);
        chat.setId(1L);
        List<Chat> chatList = new ArrayList<>();
        chatList.add(chat);
        List<Chat> emptyChatList = new ArrayList<>();
        emptyChatList = Mockito.spy(emptyChatList);
        // Test user is owner
        when(userService.getUserByUsername(any(String.class))).thenReturn(taker);
        when(chats.findByOwner(any(User.class))).thenReturn(emptyChatList);
        when(chats.findByTaker(any(User.class))).thenReturn(chatList);
        Mockito.doReturn(true).when(emptyChatList).addAll(any(ArrayList.class));

        List<Chat> savedChatList = chatService.getChatByUsername(owner.getUsername());

        assertNotNull(savedChatList);
        assertEquals(emptyChatList, savedChatList);
        verify(userService).getUserByUsername(owner.getUsername());
        verify(chats).findByOwner(taker);
        verify(chats).findByTaker(taker);
        verify(emptyChatList).addAll(chatList);
    }

    @Test
    void getChatByUsername_ValidUsernameAndHasOwnerAndTaker_ReturnChats() {
        User user1 = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        user1.setId(1L);
        User user2 = new User("taker1", "taker1@email.com", "password1");
        user2.setId(2L);
        Product product1 = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product1.setId(1L);
        Product product2 = new Product("yPhone 18 XL", "OLD", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product2.setId(2L);
        Chat chat1 = new Chat(user1, user2, product1);
        chat1.setId(1L);
        Chat chat2 = new Chat(user2, user1, product2);
        chat2.setId(2L);
        List<Chat> chatList1 = new ArrayList<>();
        chatList1.add(chat1);
        List<Chat> chatList2 = new ArrayList<>();
        chatList2.add(chat2);
        chatList2 = Mockito.spy(chatList2);
        // Test user is owner
        when(userService.getUserByUsername(any(String.class))).thenReturn(user1);
        when(chats.findByOwner(any(User.class))).thenReturn(chatList2);
        when(chats.findByTaker(any(User.class))).thenReturn(chatList1);
        Mockito.doReturn(true).when(chatList2).addAll(any(ArrayList.class));

        List<Chat> savedChatList = chatService.getChatByUsername(user1.getUsername());

        assertNotNull(savedChatList);
        assertEquals(chatList2, savedChatList);
        verify(userService).getUserByUsername(user1.getUsername());
        verify(chats).findByOwner(user1);
        verify(chats).findByTaker(user1);
        verify(chatList2).addAll(chatList1);
    }

    @Test
    void getChatByUsername_UserDoesNotExist_ReturnChats() {
        when(userService.getUserByUsername(any(String.class))).thenReturn(null);
        when(chats.findByOwner(nullable(User.class))).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class,
                () -> chatService.getChatByUsername("donkey3"));
        verify(chats).findByOwner(null);
    }

    @Test
    void getMessagesByChat_ValidChat_ReturnMessages() {
        User owner = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        owner.setId(1L);
        User taker = new User("taker1", "taker1@email.com", "password1");
        taker.setId(2L);
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setId(1L);
        Chat chat = new Chat(taker, owner, product);
        chat.setId(1L);
        Message message = new Message("a message", LocalDateTime.now().toString(), chat, owner, taker);
        message.setId(1L);
        List<Message> messageList = new ArrayList<>();
        messageList.add(message);
        when(messages.findByChat(any(Chat.class))).thenReturn(messageList);

        List<Message> savedMessageList = chatService.getMessagesByChat(chat);

        assertNotNull(savedMessageList);
        assertEquals(messageList, savedMessageList);
        verify(messages).findByChat(chat);
    }

    @Test
    void getMessagesByChat_NullParameter_ThrowsIllegalArgumentException() {
        User owner = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        owner.setId(1L);
        User taker = new User("taker1", "taker1@email.com", "password1");
        taker.setId(2L);
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setId(1L);
        Chat chat = new Chat(taker, owner, product);
        chat.setId(1L);
        Message message = new Message("a message", LocalDateTime.now().toString(), chat, owner, taker);
        message.setId(1L);
        List<Message> messageList = new ArrayList<>();
        messageList.add(message);
        when(messages.findByChat(nullable(Chat.class))).thenThrow(new IllegalArgumentException());

        assertThrows(IllegalArgumentException.class,
                () -> chatService.getMessagesByChat(null));
        verify(messages).findByChat(null);
    }

    @Test
    void addChat_ChatDoesNotExist_ReturnSavedChat() {
        User owner = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        owner.setId(1L);
        User taker = new User("taker1", "taker1@email.com", "password1");
        taker.setId(2L);
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setId(1L);
        Chat chat = new Chat(taker, owner, product);
        chat.setId(1L);
        chatService = Mockito.spy(chatService);
        Mockito.doReturn(null).when(chatService).getChatByProductIdAndTakerId(any(Long.class), any(Long.class));
        when(userService.getUser(any(Long.class))).thenReturn(taker).thenReturn(owner);
        when(productService.getProduct(any(Long.class))).thenReturn(product);
        when(chats.save(any(Chat.class))).thenReturn(chat);

        Chat savedChat = chatService.addChat(taker.getId(), owner.getId(), product.getId());

        assertNotNull(savedChat);
        assertEquals(chat, savedChat);
        verify(chatService).getChatByProductIdAndTakerId(product.getId(), taker.getId());
        verify(userService).getUser(taker.getId()); 
        verify(userService).getUser(owner.getId());
        verify(productService).getProduct(product.getId());
        // verify(chats).save(chat);
    }

    @Test
    void addChat_ChatExists_ReturnChat() {
        User owner = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        owner.setId(1L);
        User taker = new User("taker1", "taker1@email.com", "password1");
        taker.setId(2L);
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setId(1L);
        Chat chat = new Chat(taker, owner, product);
        chat.setId(1L);
        chatService = Mockito.spy(chatService);
        Mockito.doReturn(chat).when(chatService).getChatByProductIdAndTakerId(any(Long.class), any(Long.class));


        Chat savedChat = chatService.addChat(taker.getId(), owner.getId(), product.getId());

        assertNotNull(savedChat);
        assertEquals(chat, savedChat);
        verify(chatService, Mockito.times(2)).getChatByProductIdAndTakerId(product.getId(), taker.getId());
    }

    @Test
    void addChat_SameOwnerIdAndTakerId_ThrowsRuntimeException() {
        User owner = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        owner.setId(1L);
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setId(1L);
        Chat chat = new Chat(owner, owner, product);
        chat.setId(1L);
        chatService = Mockito.spy(chatService);
        Mockito.doReturn(null).when(chatService).getChatByProductIdAndTakerId(any(Long.class), any(Long.class));

        Throwable exception = assertThrows(RuntimeException.class,
                () -> chatService.addChat(owner.getId(), owner.getId(), product.getId()));
        assertEquals("Owner cannot be taker.", exception.getMessage());
        verify(chatService).getChatByProductIdAndTakerId(product.getId(), owner.getId());
    }

    @Test
    void addMessage_ValidChat_ReturnMessages() {
        User sender = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        sender.setId(1L);
        User receiver = new User("taker1", "taker1@email.com", "password1");
        receiver.setId(2L);
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setId(1L);
        Chat chat = new Chat(receiver, sender, product);
        chat.setId(1L);
        Message message = new Message("a message", LocalDateTime.now().toString(), chat, sender, receiver);
        message.setId(1L);
        chatService = Mockito.spy(chatService);
        when(userService.getUserByUsername(any(String.class))).thenReturn(sender).thenReturn(receiver);
        Mockito.doReturn(chat).when(chatService).getChatById(chat.getId());
        when(messages.save(any(Message.class))).thenReturn(message);

        Message savedMessage = chatService.addMessage(message.getContent(), message.getDateTime(), 
                sender.getUsername(), receiver.getUsername(), chat.getId());

        assertNotNull(savedMessage);
        assertEquals(message, savedMessage);
        verify(userService).getUserByUsername(sender.getUsername());
        verify(userService).getUserByUsername(receiver.getUsername());
        verify(chatService).getChatById(chat.getId());
    }

    @Test
    void addMessage_SenderUsernameAndReceiverUsernameAreEqual_ThrowsRuntimeException() {
        User sender = new User("owner1", "owner1@email.com", encoder.encode("password1"));
        sender.setId(1L);
        Product product = new Product("yPhone 15 XL", "NEW", LocalDateTime.now().toString(), "ELECTRONICS", "WHITE 512GB");
        product.setId(1L);
        Chat chat = new Chat(sender, sender, product);
        chat.setId(1L);
        Message message = new Message("a message", LocalDateTime.now().toString(), chat, sender, sender);
        message.setId(1L);

        Throwable exception = assertThrows(RuntimeException.class,
                () -> chatService.addMessage(message.getContent(), message.getDateTime(), 
                sender.getUsername(), sender.getUsername(), chat.getId()));
        assertEquals("Sender cannot be receiver.", exception.getMessage());
    }
}