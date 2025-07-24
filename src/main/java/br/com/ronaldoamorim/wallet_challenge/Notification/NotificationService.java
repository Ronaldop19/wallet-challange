package br.com.ronaldoamorim.wallet_challenge.Notification;

import org.springframework.stereotype.Service;

import br.com.ronaldoamorim.wallet_challenge.transaction.Transaction;

@Service
public class NotificationService {
    
    private final NotifyProducer notifyProducer;

    public NotificationService(NotifyProducer notifyProducer) {
        this.notifyProducer = notifyProducer;
    }

    public void notify(Transaction transaction) {
        notifyProducer.sendNotification(transaction);
    }
}
