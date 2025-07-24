package br.com.ronaldoamorim.wallet_challenge.Notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ronaldoamorim.wallet_challenge.transaction.Transaction;

@Service
public class NotifyConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyConsumer.class);
    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public NotifyConsumer(RestClient.Builder builder, ObjectMapper objectMapper) {
        this.restClient = builder.baseUrl("https://util.devi.tools/api/v1/notify").build();
        this.objectMapper = objectMapper;
    }
 
    @KafkaListener(topics = "notification-topic", groupId = "wallet-challenge")
    public void consumeNotification(Transaction transaction) {
        LOGGER.info("Notifying transaction: {}", transaction);

        try {
            restClient
                .post() 
                .body(transaction) 
                .retrieve()
                .toBodilessEntity(); // Para respostas 204 (No Content)

            LOGGER.info("Notification sent successfully for transaction: {}", transaction);

        } catch (HttpClientErrorException e) {
            // Captura erros 4xx
            LOGGER.error("Client error during notification (Status: {}): {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            String message = "Failed to notify transaction: " + transaction + " - Reason: Client error (" + e.getStatusCode() + ").";
            throw new NotificationException(message, e);
        } catch (HttpServerErrorException.GatewayTimeout e) {
            LOGGER.error("Notification service gateway timeout (504): {}", e.getResponseBodyAsString(), e);
            String errorMessage = "The service is not available, try again later."; // Mensagem padrão de fallback
            try {
                NotificationResponse errorResponse = objectMapper.readValue(e.getResponseBodyAsString(), NotificationResponse.class);
                if (errorResponse != null && errorResponse.message() != null) {
                    errorMessage = errorResponse.message();
                }
            } catch (Exception parseException) {
                LOGGER.warn("Failed to parse 504 error response body: {}", parseException.getMessage());
            }
            throw new NotificationException("Failed to notify transaction: " + transaction + " - Reason: " + errorMessage, e);
        } catch (HttpServerErrorException e) {
            LOGGER.error("Server error during notification (Status: {}): {}", e.getStatusCode(), e.getResponseBodyAsString(), e);
            String message = "Failed to notify transaction: " + transaction + " - Reason: Server error (" + e.getStatusCode() + ").";
            throw new NotificationException(message, e);
        } catch (Exception e) {
            LOGGER.error("Unexpected error during notification: {}", e.getMessage(), e);
            throw new NotificationException("Failed to notify transaction: " + transaction + " - Reason: Unexpected error.", e);
        }
    }
}
