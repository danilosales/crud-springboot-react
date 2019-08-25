package br.com.danilosales.crud.dto;

import br.com.danilosales.crud.dto.JwtTokenResponse;
import java.io.Serializable;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

public class JwtTokenResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final String token;
	
	private final boolean admin;

	public JwtTokenResponse(String token, Collection<? extends GrantedAuthority> authorities) {
		this.token = token;
		this.admin = authorities.stream()
				.filter(aut -> aut.getAuthority().equals("ADMIN"))
				.findFirst().isPresent();
	}

	public String getToken() {
		return this.token;
	}

	public boolean isAdmin() {
		return this.admin;
	}
}
