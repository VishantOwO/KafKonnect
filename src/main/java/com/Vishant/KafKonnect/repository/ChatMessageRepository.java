package com.Vishant.KafKonnect.repository;

import com.Vishant.KafKonnect.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByTopicOrderByTimestampAsc(String topic);
}