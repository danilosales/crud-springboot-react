package br.com.danilosales.crud.dto;

import br.com.danilosales.crud.dto.ClienteDTO;
import br.com.danilosales.crud.dto.ClienteTelefone;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public class ClienteDTO {
	
	private Long id;
	
	@NotBlank(message = "O nome é obrigatório")
	@Size(min = 3, max = 100, message = "Informe entre 3 a 100 caracteres")
	private String nome;
	
	@CPF(message = "O CPF é inválido")
	@NotBlank(message = "O CPF é obrigatório")
	@Size(min = 11, max = 11, message = "O cpf deve conter 11 caracteres")
	private String cpf;
	
	@NotBlank(message = "O CEP é obrigatório")
	@Size(min = 8, max = 8, message = "O CEP deve conter 8 caracteres")
	private String cep;
	
	@NotBlank(message = "O logradouro é obrigatório")
	private String logradouro;
	
	private String complemento;
	
	@NotBlank(message = "O bairro é obrigatório")
	private String bairro;
	
	@NotBlank(message = "O número é obrigatório")
	private String numero;
	
	@NotBlank(message = "A cidade é obrigatória")
	private String cidade;
	
	@NotBlank(message = "A uf é obrigatória")
	private String uf;
	
	@Size(min = 1, message = "Informe ao menos um telefone para contato")
	private List<ClienteTelefone> telefones = new ArrayList<>();

	@Size(min = 1, message = "Informe ao menos um e-mail")
	private List<String> emails = new ArrayList<>();

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<ClienteTelefone> getTelefones() {
		return this.telefones;
	}

	public void setTelefones(List<ClienteTelefone> telefones) {
		this.telefones = telefones;
	}

	public List<String> getEmails() {
		return this.emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
}
