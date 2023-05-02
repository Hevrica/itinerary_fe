package itinerary.itinerary_fe.config.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import itinerary.itinerary_fe.dto.JwtDto;
import lombok.extern.slf4j.Slf4j;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider {
	
	private final Key key;
	
	public JwtProvider(@Value("${jwt.secret}") String secret) {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}
	
	public JwtDto createToken(Authentication authentication) {
		String authorities = authentication
				.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		
		long now = (new Date()).getTime();
		Date validity = new Date(now + 86400 * 1000);
		
		String accessToken = Jwts.builder()
				.setSubject(authentication.getName())
				.claim("auth", authorities)
				.setExpiration(validity)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		String refreshToken = Jwts.builder()
				.setExpiration(validity)
				.signWith(key, SignatureAlgorithm.HS256)
				.compact();
		
		return JwtDto.builder()
				.grantType("Bearer")
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
				
	}
	
	public Authentication getAuthentication(String token) {
		Claims claims = parseClaims(token);
		
		if(claims.get("auth") == null)
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		
		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get("auth").toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
		UserDetails principal = new User(claims.getSubject(), "", authorities);
		
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT");
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT");
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT");
		} catch (IllegalArgumentException e) {
			log.info("Empty Claims String JWT");
		}
		return false;
	}
	
	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}
}