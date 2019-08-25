package br.com.danilosales.crud.service.exception;

public class ClienteJaCadastrado extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClienteJaCadastrado(String mensagem) {
		super(mensagem);
	}
	
}
