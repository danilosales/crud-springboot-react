package br.com.danilosales.crud.controller;

import br.com.danilosales.crud.controller.JwtAuthenticationRestController;
import br.com.danilosales.crud.dto.JwtTokenRequest;
import br.com.danilosales.crud.dto.JwtTokenResponse;
import br.com.danilosales.crud.security.JwtTokenUtil;
import br.com.danilosales.crud.security.exception.AuthenticationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Autenticação")
@RestController
@CrossOrigin(origins = { "http://localhost:3000", "http://crud-cliente-frontend:3000" })
public class JwtAuthenticationRestController {
	
	@Value("${jwt.request-header}")
	private String tokenHeader;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDetailsService userDetailsService;

	@ApiOperation("Autenticação do usuário")
	@PostMapping(path = "/auth/login")
	public ResponseEntity<?> autenticar(@Valid @RequestBody JwtTokenRequest tokenRequest)
			throws AuthenticationException {
		
		try {
			
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(tokenRequest.getUsername(), tokenRequest.getPassword()));
		
		} catch (DisabledException e) {
			throw new AuthenticationException("O usuário está desabilitado");
		} catch (BadCredentialsException e) {
			throw new AuthenticationException("Credenciais inválidas");
		}

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(tokenRequest.getUsername());

		String token = this.jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtTokenResponse(token, userDetails.getAuthorities()));
	}

	@ApiOperation("Atualizar token do usuário")
	@GetMapping(path = "/auth/refreshToken" )
	public ResponseEntity<?> refreshToken(HttpServletRequest request) {
		
		String authToken = request.getHeader(this.tokenHeader);
		String token = authToken.substring(7);
		String username = this.jwtTokenUtil.getUsernameFromToken(token);

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

		if (this.jwtTokenUtil.canTokenBeRefreshed(token)) {
			String refreshToken = this.jwtTokenUtil.refreshToken(token);
			return ResponseEntity.ok(new JwtTokenResponse(refreshToken, userDetails.getAuthorities()));
		}
		
		return ResponseEntity.badRequest().build();
	}
}
