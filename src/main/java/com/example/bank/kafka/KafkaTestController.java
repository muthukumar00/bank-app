package com.example.bank.kafka;

import java.math.BigDecimal;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
@Profile("!demo")
public class KafkaTestController {

    private final KafkaProducerService kafkaProducerService;

    public KafkaTestController(
            KafkaProducerService kafkaProducerService) {

        this.kafkaProducerService =
                kafkaProducerService;
    }

    @PostMapping("/test")
    public String testKafka() {

        TransactionEvent event =
                new TransactionEvent();

        event.setTransactionId(1001L);
        event.setFromAccountId(1);
        event.setToAccountId(2);
        event.setAmount(new BigDecimal("8000"));
        event.setTransactionType("TRANSFER");
        event.setStatus("SUCCESS");

        kafkaProducerService
                .sendTransactionEvent(event);

        return "Kafka event sent";
    }
}