package com.Vishant.KafKonnect.task;

import com.Vishant.KafKonnect.service.ChatService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DatabaseMaintenanceTask {

    private final ChatService chatService;

    public DatabaseMaintenanceTask(ChatService chatService) {
        this.chatService = chatService;
    }

    @Scheduled(cron = "0 0 3 * * ?") // Runs daily at 3 AM
    public void cleanupOldMessages() {
        LocalDateTime cutoff = LocalDateTime.now().minusMonths(1);
        chatService.deleteMessagesOlderThan(cutoff);
    }
}