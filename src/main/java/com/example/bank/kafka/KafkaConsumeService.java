package com.example.bank.kafka;

import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;

@Service
@Profile("!demo")
public class KafkaConsumeService {
	
	@RetryableTopic(
		    attempts = "4",
		    backOff = @BackOff(delay = 2000),
		    dltTopicSuffix = ".DLT"
	)
	
	@KafkaListener(
        topics = "bank-transactions",
        groupId = "bank-transaction-group"
    )

	public void consume(TransactionEvent event) {
		
		System.out.println("========== KAFKA MESSAGE RECEIVED ==========");

        System.out.println(
            "Received Transaction ID: "
            + event.getTransactionId()
        );

        System.out.println(
            "From Account: "
            + event.getFromAccountId()
        );

        System.out.println(
            "To Account: "
            + event.getToAccountId()
        );

        System.out.println(
            "Amount: "
            + event.getAmount()
        );

        System.out.println(
            "Status: "
            + event.getStatus()
        );
        
        System.out.println("=========================================");
        
       throw new RuntimeException(
                "Testing Kafka failure"
            );
    }
}
