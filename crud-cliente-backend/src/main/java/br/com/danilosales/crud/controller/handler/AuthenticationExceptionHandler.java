package br.com.danilosales.crud.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.danilosales.crud.security.exception.AuthenticationException;

@RestControllerAdvice
public class AuthenticationExceptionHandler {
	
	@ExceptionHandler({ AuthenticationException.class, UsernameNotFoundException.class })
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
}
