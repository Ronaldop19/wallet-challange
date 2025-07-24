package br.com.ronaldoamorim.wallet_challenge.wallet;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Table("WALLETS")
public record Wallet(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id,
    String fullName,
    Long cpf,
    String email,
    String password,
    int type,
    BigDecimal balance
) {

    public Wallet debit(BigDecimal value) {
         return new Wallet(
            id,
            fullName,
            cpf,
            email,
            password,
            type,
            balance.subtract(value));
    }

    public Wallet credit(BigDecimal value) {
        return new Wallet(
                id,
                fullName,
                cpf,
                email,
                password,
                type,
                balance.add(value));
    }
}
