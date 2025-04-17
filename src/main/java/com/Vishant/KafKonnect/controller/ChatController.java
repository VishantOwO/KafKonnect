package com.Vishant.KafKonnect.controller;

import com.Vishant.KafKonnect.model.ChatMessage;
import com.Vishant.KafKonnect.model.User;
import com.Vishant.KafKonnect.service.ChatService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat")
    public String chatPage(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "general") String topic,
            @PageableDefault(size = 20) Pageable pageable,
            Model model) {

        Page<ChatMessage> messages = chatService.getLatestMessagesByTopic(topic, pageable);
        model.addAttribute("messages", messages);
        model.addAttribute("topic", topic);
        model.addAttribute("allTopics", chatService.getAllTopics());
        model.addAttribute("currentUser", user);
        return "chat";
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage chatMessage, @AuthenticationPrincipal User user) {
        chatMessage.setUser(user);
        chatMessage.setTimestamp(LocalDateTime.now());
        return chatService.saveMessage(chatMessage);
    }

    @GetMapping("/chat/history")
    public String chatHistory(
            @AuthenticationPrincipal User user,
            @PageableDefault(size = 50) Pageable pageable,
            Model model) {

        Page<ChatMessage> messages = chatService.getUserMessages(user.getId(), pageable);
        model.addAttribute("messages", messages);
        model.addAttribute("currentUser", user);
        return "chat-history";
    }
}