package csd.app.controllers;

import java.util.*;

import csd.app.chat.*;
import csd.app.notification.Notification;
import csd.app.notification.NotificationService;
import csd.app.product.*;
import csd.app.payload.request.ChatRequest;
import csd.app.payload.request.MessageRequest;
import csd.app.payload.response.ChatResponse;
import csd.app.payload.response.ChatMessageResponse;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
public class ChatController {
    
    @Autowired
    private ChatService chatService;

    @Autowired
    private ProductService productService;

    @Autowired
    private NotificationService notificationService;

    public ChatController(ChatService chatService, ProductService productService) {
        this.chatService = chatService;
        this.productService = productService;
    }

    @GetMapping("/api/chat")
    public List<ChatResponse> getChatByUsername(@RequestParam("username") @PathVariable String username) {
        List<Chat> chats = chatService.getChatbyUsername(username);
        List<ChatResponse> resp = new ArrayList<>();
        for (Chat chat : chats) {
            ChatResponse chatResp = new ChatResponse(chat.getId(), chat.getOwner().getId(), 
                    chat.getOwner().getUsername(), chat.getTaker().getId(), chat.getTaker().getUsername(),
                    chat.getProduct().getId(), chat.getProduct().getProductName(),
                    chat.getProduct().getImageUrl());
            resp.add(chatResp);
        }
        return resp;
    }

    @GetMapping("/api/chat/{id}")
    public ChatResponse getChatById(@PathVariable Long id) {
        Chat chat = chatService.getChatById(id);
        ChatResponse resp = new ChatResponse(chat.getId(), chat.getOwner().getId(), 
                chat.getOwner().getUsername(), chat.getTaker().getId(), chat.getTaker().getUsername(),
                chat.getProduct().getId(), chat.getProduct().getProductName(),
                chat.getProduct().getImageUrl());
        return resp;
    }

    @GetMapping("/api/chat/{id}/messages")
    public List<ChatMessageResponse> getMessages(@PathVariable Long id) {
        Chat chat = chatService.getChatById(id);
        // make a chatMessageResponse that returns content datetime chatid and usernames
        List<Message> messages = chatService.getMessagesByChat(chat);
        List<ChatMessageResponse> resp = new ArrayList<>();
        for (Message message : messages) {
            ChatMessageResponse chatMessageResp = new ChatMessageResponse(message.getContent(),
                    message.getDateTime(), message.getSender().getId(),
                    message.getSender().getUsername(), message.getReceiver().getId(),
                    message.getReceiver().getUsername(), message.getChat().getId());
            resp.add(chatMessageResp);
        }
        return resp;
    }

    @PostMapping("/api/chat")
    public ChatResponse createChat(@Valid @RequestBody ChatRequest chatRequest) {
        Chat chat = new Chat();
        Long ownerId = productService.getProduct(chatRequest.getProductId()).getUser().getId();
        Chat savedChat = chatService.addChat(chat, chatRequest.getTakerId(), ownerId, chatRequest.getProductId());
        if (savedChat == null) {
            throw new RuntimeException("Taker cannot be Owner!"); // handled in frontend
        }
        notificationService.addNotification((new Notification(savedChat, false)));
        return new ChatResponse(savedChat.getId(), savedChat.getOwner().getId(), 
        savedChat.getOwner().getUsername(), savedChat.getTaker().getId(), savedChat.getTaker().getUsername(),
        savedChat.getProduct().getId(), savedChat.getProduct().getProductName(),
        savedChat.getProduct().getImageUrl());
    }

    @PostMapping("/api/chat/{id}/messages")
    public ChatMessageResponse addMessage(@Valid @RequestBody MessageRequest messageRequest) {
        Message message = new Message(messageRequest.getContent(), messageRequest.getDateTime());
        Message savedMessage = chatService.addMessage(message, messageRequest.getSenderUsername(),
                messageRequest.getReceiverUsername(), messageRequest.getChatId());
                
        Notification chatNotification = new Notification(savedMessage.getChat(), false);
        chatNotification.setMessageContent(savedMessage.getContent());
        notificationService.addNotification(chatNotification);

        ChatMessageResponse resp = new ChatMessageResponse(savedMessage.getContent(),
                savedMessage.getDateTime(), savedMessage.getSender().getId(),
                savedMessage.getSender().getUsername(), savedMessage.getReceiver().getId(),
                savedMessage.getReceiver().getUsername(), savedMessage.getChat().getId());
        return resp;
    }
}
