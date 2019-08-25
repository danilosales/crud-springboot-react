package br.com.danilosales.crud.controller.handler;

import br.com.danilosales.crud.service.exception.EntidadeInexistente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EntidadeInexisteExceptionHandler {
	
	@ExceptionHandler({ EntidadeInexistente.class })
	public ResponseEntity<String> handleEntidadeInexistenteException(EntidadeInexistente e) {
		return ResponseEntity.noContent().build();
	}
}
