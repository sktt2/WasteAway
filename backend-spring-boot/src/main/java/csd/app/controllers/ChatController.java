package csd.app.controllers;

import java.util.*;
import java.net.*;

import csd.app.chat.*;
import csd.app.notification.Notification;
import csd.app.notification.NotificationService;
import csd.app.payload.request.ChatRequest;
import csd.app.payload.request.MessageRequest;
import csd.app.payload.response.ChatResponse;
import csd.app.payload.response.ChatMessageResponse;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ChatController {

    private ChatService chatService;
    private NotificationService notificationService;

    @Autowired
    public ChatController(ChatService chatService, NotificationService notificationService) {
        this.chatService = chatService;
        this.notificationService = notificationService;
    }

    @GetMapping("/api/chat")
    public ResponseEntity<?> getChatByUsername(@RequestParam("username") @PathVariable String username) {
        List<Chat> chats = chatService.getChatByUsername(username);
        List<ChatResponse> response = new ArrayList<>();
        for (Chat chat : chats) {
            ChatResponse chatResp = new ChatResponse(chat.getId(), chat.getOwner(),
                    chat.getTaker(), chat.getProduct());
            response.add(chatResp);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/chat/{id}")
    public ResponseEntity<?> getChatById(@PathVariable Long id) {
        Chat chat = chatService.getChatById(id);
        ChatResponse response = new ChatResponse(chat.getId(), chat.getOwner(),
                chat.getTaker(), chat.getProduct());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/chat/{id}/messages")
    public ResponseEntity<?> getMessages(@PathVariable Long id) {
        Chat chat = chatService.getChatById(id);
        // make a chatMessageResponse that returns content datetime chatid and usernames
        List<Message> messages = chatService.getMessagesByChat(chat);
        List<ChatMessageResponse> response = new ArrayList<>();
        for (Message message : messages) {
            ChatMessageResponse chatMessageResp = new ChatMessageResponse(message, 
                    message.getSender(), message.getReceiver(), message.getChat().getId());
            response.add(chatMessageResp);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/chat")
    public ResponseEntity<?> createChat(@Valid @RequestBody ChatRequest chatRequest) {
        Chat savedChat = chatService.addChat(chatRequest.getTakerId(), chatRequest.getOwnerId(),
                chatRequest.getProductId());
        notificationService.addNotification((new Notification(savedChat, savedChat.getTaker(), savedChat.getOwner(), false)));
        ChatResponse response = new ChatResponse(savedChat.getId(), savedChat.getOwner(),
                savedChat.getTaker(), savedChat.getProduct());
        return ResponseEntity.created(URI.create("/api/chat")).body(response);
    }

    @PostMapping("/api/chat/{id}/messages")
    public ResponseEntity<?> addMessage(@Valid @RequestBody MessageRequest messageRequest) {
        Message savedMessage = chatService.addMessage(messageRequest.getContent(), messageRequest.getDateTime(),
                messageRequest.getSenderUsername(), messageRequest.getReceiverUsername(),
                messageRequest.getChatId());

        Notification chatNotification = new Notification(savedMessage.getChat(), savedMessage.getSender(), savedMessage.getReceiver(), false);
        chatNotification.setMessageContent(savedMessage.getContent());
        notificationService.addNotification(chatNotification);

        ChatMessageResponse response = new ChatMessageResponse(savedMessage, 
        savedMessage.getSender(), savedMessage.getReceiver(), savedMessage.getChat().getId());
        return ResponseEntity.created(URI.create("/api/chat/" + messageRequest.getChatId() + "/messages"))
                .body(response);
    }
}
