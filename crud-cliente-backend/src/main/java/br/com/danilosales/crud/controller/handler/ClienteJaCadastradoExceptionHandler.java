package br.com.danilosales.crud.controller.handler;

import br.com.danilosales.crud.service.exception.ClienteJaCadastrado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClienteJaCadastradoExceptionHandler {
	
	@ExceptionHandler({ ClienteJaCadastrado.class })
	public ResponseEntity<String> handleClienteJaCadastradoException(ClienteJaCadastrado e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
