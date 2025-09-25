package com.banking.transaction_ms.ka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {
    @KafkaListener(topics = "transaction_topic", groupId = "transaction_group")
    public void consume(String message) {
        System.out.println("Mensaje recibido: " + message);
    }

}
