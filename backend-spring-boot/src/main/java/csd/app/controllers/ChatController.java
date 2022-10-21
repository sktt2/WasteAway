package csd.app.controllers;

import java.util.*;

import csd.app.chat.*;
import csd.app.payload.request.ChatRequest;
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

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/api/chat/{id}")
    public List<Message> getChat(@Valid @RequestBody ChatRequest chatRequest) {
        Chat chat = chatService.getChat(chatRequest.getProductId(), chatRequest.getTakerId());
        List<Message> messages = chatService.getMessagesByChat(chat);
        return messages;
    }

    @PostMapping("/api/chat")
    public ResponseEntity<?> createChat(@Valid @RequestBody ChatRequest chatRequest) {
        Chat chat = new Chat();
        Chat savedChat = chatService.addChat(chat, chatRequest.getTakerId(), chatRequest.getProductId());
        if (savedChat == null) {
            return ResponseEntity.badRequest()
                .body(new MessageResponse("Owner cannot create chat with self!"));
        }
        return ResponseEntity.ok(new MessageResponse("Chat created successfully!"));
    }
}
