package br.com.ronaldoamorim.wallet_challenge;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@EnableJdbcAuditing
@SpringBootApplication
public class WalletChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletChallengeApplication.class, args);
	}

	@Bean
	NewTopic notificationTopic() {
		return new NewTopic("notification-topic", 1, (short) 1);
	}
}