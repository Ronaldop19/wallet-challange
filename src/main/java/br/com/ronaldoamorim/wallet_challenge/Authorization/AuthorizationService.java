package br.com.ronaldoamorim.wallet_challenge.Authorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import br.com.ronaldoamorim.wallet_challenge.transaction.Transaction;

@Service
public class AuthorizationService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationService.class);
    private final RestClient restClient;

    public AuthorizationService(RestClient.Builder builder) {
        this.restClient = builder.baseUrl("https://util.devi.tools/api/v2/authorize").build();
    }

    public void authorize(Transaction transaction) {
        LOGGER.info("Authorizing transaction: {}", transaction);

        try {
            var response = restClient
                .get()
                .retrieve()
                .toEntity(Authorization.class);

            LOGGER.info("Response: {}", response.getBody());

            if (response.getBody() == null || !response.getBody().isAuthorized()) {
                throw new UnauthorizatedException("Transaction not authorized: " + transaction + " - Reason: Authorization data is missing or false in 2xx response.");
            }
            LOGGER.info("Transaction authorized: {}", transaction);
            
        } catch (HttpClientErrorException.Forbidden e) {
            // Captura especificamente o 403 Forbidden
            LOGGER.error("Authorization failed with 403 Forbidden: {}", e.getMessage());
            throw new UnauthorizatedException("Transaction not authorized - Reason: Service returned 403 Forbidden: " + transaction);
        } catch (Exception e) {
            
            LOGGER.error("Unexpected error during authorization: {}", e.getMessage());
            throw new UnauthorizatedException("Transaction not authorized: " + transaction);
        }
    }
}
