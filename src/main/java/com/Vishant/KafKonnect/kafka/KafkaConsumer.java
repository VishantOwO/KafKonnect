package com.Vishant.KafKonnect.kafka;

import com.Vishant.KafKonnect.model.ChatMessage;
import com.Vishant.KafKonnect.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @KafkaListener(topics = "chat_messages", groupId = "chat-group")
    public void consume(String message) {
        System.out.println("Consumed message: " + message);

        // Here you would parse the message and save to DB
        // For simplicity, we'll just forward to WebSocket
        template.convertAndSend("/topic/messages", message);
    }
}