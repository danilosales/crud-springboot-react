package br.com.danilosales.crud.security.exception;

public class AuthenticationException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthenticationException(String mensagem) {
		super(mensagem);
	}
	
}
