package br.com.danilosales.crud.dto;

import br.com.danilosales.crud.dto.ClienteTelefone;
import br.com.danilosales.crud.model.TipoTelefone;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClienteTelefone {
	
	@NotNull(message = "É obrigatório informar um tipo de telefone")
	private TipoTelefone tipo;
	
	@NotBlank(message = "O número não pode ser nulo")
	private String numero;

	public TipoTelefone getTipo() {
		return this.tipo;
	}

	public void setTipo(TipoTelefone tipo) {
		this.tipo = tipo;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}
}
