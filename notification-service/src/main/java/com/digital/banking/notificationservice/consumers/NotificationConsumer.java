package com.digital.banking.notificationservice.consumers;

import com.digital.banking.notificationservice.dto.TransactionCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(topics = "transaction-events", groupId = "notification-service-group")
    public void handleTransactionCreated(TransactionCreatedEvent event) {
        System.out.println("📩 Sending notification to " + event.getUserEmail() +
                " for transaction " + event.getTransactionId());
        // TODO: send email
    }
}

