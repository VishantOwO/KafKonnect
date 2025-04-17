package com.Vishant.KafKonnect.repository;

import com.Vishant.KafKonnect.model.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT m FROM ChatMessage m WHERE m.topic = :topic ORDER BY m.timestamp DESC")
    Page<ChatMessage> findLatestByTopic(@Param("topic") String topic, Pageable pageable);

    List<ChatMessage> findByTopicOrderByTimestampAsc(String topic);

    @Query("SELECT DISTINCT m.topic FROM ChatMessage m")
    List<String> findAllTopics();

    @Query("SELECT m FROM ChatMessage m WHERE m.user.id = :userId ORDER BY m.timestamp DESC")
    Page<ChatMessage> findByUserId(@Param("userId") Long userId, Pageable pageable);

    void deleteByTimestampBefore(LocalDateTime date);
}