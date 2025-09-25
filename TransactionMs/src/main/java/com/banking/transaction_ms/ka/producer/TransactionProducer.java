package com.banking.transaction_ms.ka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class TransactionProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    public TransactionProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendTransaction(String transactionMessage) {
        kafkaTemplate.send("transaction_topi",transactionMessage);

        }


    }

