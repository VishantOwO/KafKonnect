package com.Vishant.KafKonnect.service;

import com.Vishant.KafKonnect.model.ChatMessage;
import com.Vishant.KafKonnect.model.User;
import com.Vishant.KafKonnect.repository.ChatMessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;

    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Transactional
    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }

    public Page<ChatMessage> getLatestMessagesByTopic(String topic, Pageable pageable) {
        return chatMessageRepository.findLatestByTopic(topic, pageable);
    }

    public List<ChatMessage> getAllMessagesByTopic(String topic) {
        return chatMessageRepository.findByTopicOrderByTimestampAsc(topic);
    }

    public List<String> getAllTopics() {
        return chatMessageRepository.findAllTopics();
    }

    @Transactional
    public void deleteMessagesOlderThan(LocalDateTime date) {
        chatMessageRepository.deleteByTimestampBefore(date);
    }

    public Page<ChatMessage> getUserMessages(Long userId, Pageable pageable) {
        return chatMessageRepository.findByUserId(userId, pageable);
    }
}