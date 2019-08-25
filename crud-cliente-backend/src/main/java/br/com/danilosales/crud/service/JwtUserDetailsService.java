package br.com.danilosales.crud.service;

import br.com.danilosales.crud.security.JwtUserDetails;
import br.com.danilosales.crud.service.JwtUserDetailsService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	static List<JwtUserDetails> listaUsuarios = new ArrayList<>();

	static {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		listaUsuarios.add(new JwtUserDetails("admin", bCryptPasswordEncoder.encode("123456"), "ADMIN"));
		listaUsuarios.add(new JwtUserDetails("comum", bCryptPasswordEncoder.encode("123456"), "COMUM"));
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<JwtUserDetails> usuarioExiste = listaUsuarios.stream()
				.filter(user -> user.getUsername().equals(username)).findFirst();

		if (!usuarioExiste.isPresent()) {
			throw new UsernameNotFoundException(
					String.format("Usuário não encontrado '%s'.", new Object[] { username }));
		}

		return (UserDetails) usuarioExiste.get();
	}
}
