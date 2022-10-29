package csd.app.controllers;

import java.util.*;

import csd.app.chat.*;
import csd.app.product.*;
import csd.app.payload.request.ChatRequest;
import csd.app.payload.request.MessageRequest;
import csd.app.payload.response.ChatResponse;
import csd.app.payload.response.MessageResponse;

import org.springframework.http.ResponseEntity;
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
    public List<Message> getMessages(@Valid @RequestBody ChatRequest chatRequest) {
        Chat chat = chatService.getChatByProductIdAndTakerId(chatRequest.getProductId(), chatRequest.getTakerId());
        List<Message> messages = chatService.getMessagesByChat(chat);
        return messages;
    }

    @PostMapping("/api/chat")
    public ChatResponse createChat(@Valid @RequestBody ChatRequest chatRequest) {
        Chat chat = new Chat();
        Long ownerId = productService.getProduct(chatRequest.getProductId()).getUser().getId();
        Chat savedChat = chatService.addChat(chat, chatRequest.getTakerId(), ownerId, chatRequest.getProductId());
        if (savedChat == null) {
            throw new RuntimeException("Taker cannot be Owner!"); // handled in frontend
        }
        return new ChatResponse(savedChat.getId());
    }

    @PostMapping("/api/chat/{id}")
    public Message addMessage(@Valid @RequestBody MessageRequest messageRequest) {
        Message message = new Message(messageRequest.getContent(), messageRequest.getDateTime());
        return chatService.addMessage(message, messageRequest.getSenderUsername(),
                messageRequest.getReceiverUsername(), messageRequest.getChatId());
    }
}
