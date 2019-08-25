package br.com.danilosales.crud.dto;

import br.com.danilosales.crud.dto.JwtTokenRequest;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;

public class JwtTokenRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Informe o username")
	private String username;
	
	@NotBlank(message = "Informe o password")
	private String password;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
