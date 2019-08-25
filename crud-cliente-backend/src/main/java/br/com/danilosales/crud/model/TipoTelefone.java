package br.com.danilosales.crud.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoTelefone {
	
	RESIDENCIAL("residencial", "Residencial"), 
	COMERCIAL("comercial", "Comercial"), 
	CELULAR("celular", "Celular");

	@JsonValue
	private String codigo;

	private String descricao;

	TipoTelefone(String codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public static TipoTelefone getByCodigo(String codigo) {
		return (TipoTelefone) Arrays.stream(values()).filter(item -> item.codigo.equals(codigo)).findFirst()
				.orElseThrow(IllegalArgumentException::new);
	}
}
