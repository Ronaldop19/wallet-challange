package br.com.ronaldoamorim.wallet_challenge.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Table("TRANSACTIONS")
public record Transaction(
    @Id @GeneratedValue(strategy = GenerationType.UUID) UUID id,
    Long payer,
    Long payee,
    BigDecimal value,
    @CreatedDate LocalDateTime createdAt) {
    
    public Transaction {
        value = value.setScale(2);
    }
}
