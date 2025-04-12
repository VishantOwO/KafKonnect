package com.Vishant.KafKonnect.controller;

import com.Vishant.KafKonnect.kafka.KafkaProducer;
import com.Vishant.KafKonnect.model.ChatMessage;
import com.Vishant.KafKonnect.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @GetMapping("/")
    public String chatPage(Model model, @RequestParam(defaultValue = "general") String topic) {
        List<ChatMessage> messages = chatMessageRepository.findByTopicOrderByTimestampAsc(topic);
        model.addAttribute("messages", messages);
        model.addAttribute("topic", topic);
        return "chat";
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        chatMessage.setTimestamp(LocalDateTime.now());
        chatMessageRepository.save(chatMessage);
        kafkaProducer.sendMessage(chatMessage.getSender() + ": " + chatMessage.getContent());
        return chatMessage;
    }
}