package br.com.ronaldoamorim.wallet_challenge.Authorization;

public class UnauthorizatedException extends RuntimeException {
    
    public UnauthorizatedException(String message) {
        super(message);
    }
}
