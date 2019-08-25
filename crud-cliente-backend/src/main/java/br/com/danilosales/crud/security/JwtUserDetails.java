package br.com.danilosales.crud.security;

import br.com.danilosales.crud.security.JwtUserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private String password;
	
	private final Collection<? extends GrantedAuthority> authorities;

	public JwtUserDetails(String username, String password, String role) {
		this.username = username;
		this.password = password;

		List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(role));

		this.authorities = authorities;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	public String getUsername() {
		return this.username;
	}

	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	public boolean isEnabled() {
		return true;
	}
}
