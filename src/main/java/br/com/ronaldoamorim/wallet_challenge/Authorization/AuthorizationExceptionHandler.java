package br.com.ronaldoamorim.wallet_challenge.Authorization;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthorizationExceptionHandler {
    @ExceptionHandler(UnauthorizatedException.class)
    public ResponseEntity<Object> handle(UnauthorizatedException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }   
}
