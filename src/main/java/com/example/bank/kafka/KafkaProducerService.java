package com.example.bank.kafka;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Profile("!demo")
public class KafkaProducerService {

    private static final String TOPIC = "bank-transactions";
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public KafkaProducerService(
            KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTransactionEvent(TransactionEvent event) {
        kafkaTemplate.send(
            TOPIC,
            event.getTransactionId().toString(),
            event
        );
    }
}