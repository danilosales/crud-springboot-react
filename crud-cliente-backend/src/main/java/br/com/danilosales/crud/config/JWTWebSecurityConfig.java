package br.com.danilosales.crud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.danilosales.crud.security.JwtTokenAuthorizationOncePerRequestFilter;
import br.com.danilosales.crud.security.JwtUnAuthorizedResponseAuthenticationEntryPoint;
import br.com.danilosales.crud.service.JwtUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JWTWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;
	
	@Autowired
	private JwtUserDetailsService jwtuserDetailsService;
	
	@Autowired
	private JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.jwtuserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().exceptionHandling()
			.authenticationEntryPoint(this.jwtUnAuthorizedResponseAuthenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests().anyRequest().authenticated();

		httpSecurity.addFilterBefore(this.jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

		httpSecurity.headers().frameOptions().sameOrigin().cacheControl();

		httpSecurity.cors();
	}

	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity
			.ignoring()
				.antMatchers(HttpMethod.POST, new String[] { "/auth/login", "/auth/refreshToken" })
				.antMatchers(HttpMethod.OPTIONS, new String[] { "/**" })
			.and().ignoring()
				.antMatchers(HttpMethod.GET, "/")
			.and().ignoring()	
				.antMatchers(new String[] { "/swagger-ui.html" })
				.antMatchers(new String[] { "/swagger-resources/**" })
				.antMatchers(new String[] { "/v2/**" })
				.antMatchers(new String[] { "/webjars/**" })
				.antMatchers(new String[] { "/h2-console/**/**" });
	}
}
