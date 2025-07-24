package br.com.ronaldoamorim.wallet_challenge.Notification;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import br.com.ronaldoamorim.wallet_challenge.transaction.Transaction;

@Service
public class NotifyProducer {
    
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public NotifyProducer(KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(Transaction transaction) {
        kafkaTemplate.send("notification-topic", transaction);
    }

}
