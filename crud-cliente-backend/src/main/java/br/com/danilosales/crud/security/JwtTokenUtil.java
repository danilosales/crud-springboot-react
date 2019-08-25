package br.com.danilosales.crud.security;

import br.com.danilosales.crud.security.JwtTokenUtil;
import br.com.danilosales.crud.security.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {
	
	private Clock clock = DefaultClock.INSTANCE;

	@Value("${jwt.signing-key}")
	private String signingKey;

	@Value("${jwt.token-expiration}")
	private Long expirationTime;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	public Boolean canTokenBeRefreshed(String token) {
		return isTokenExpired(token) && !ignoreTokenExpiration(token);
	}

	public String refreshToken(String token) {
		Date createdDate = this.clock.now();
		Date expirationDate = calculateExpirationDate(createdDate);

		Claims claims = getAllClaimsFromToken(token);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);

		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, this.signingKey).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		JwtUserDetails user = (JwtUserDetails) userDetails;
		String username = getUsernameFromToken(token);
		
		return username.equals(user.getUsername()) && !isTokenExpired(token);
	}

	private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getAllClaimsFromToken(token);
		return (T) claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return (Claims) Jwts.parser().setSigningKey(this.signingKey).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return Boolean.valueOf(expiration.before(this.clock.now()));
	}

	private Boolean ignoreTokenExpiration(String token) {
		return Boolean.valueOf(false);
	}

	private Date calculateExpirationDate(Date createdDate) {
		return new Date(createdDate.getTime() + this.expirationTime.longValue() * 1000L);
	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		Date createdDate = this.clock.now();
		Date expirationDate = calculateExpirationDate(createdDate);

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(createdDate)
				.setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, this.signingKey).compact();
	}
}
