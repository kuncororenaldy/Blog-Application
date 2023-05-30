package com.blog.app.util;

import java.security.SignatureException;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.blog.app.service.impl.UserDetailsImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtils {

  @Value("${blog.apps.jwtSecret}")
  private String jwtSecret;
	  
  @Value("${blog.apps.jwtExpirationMs}")
  private int jwtExpirationMs;

  @Value("${blog.apps.jwtCookieName}")
  private String jwtCookie;
  
  
  public String getJwtFromCookies(HttpServletRequest request) {
	  Cookie cookie = WebUtils.getCookie(request, jwtCookie);
	  if (cookie != null) {
	      return cookie.getValue();
	  } else {
		  return null;
	}
  }

  public ResponseCookie generateJwtCookie(UserDetailsImpl userPrincipal) {
	  String jwt = generateTokenFromUsername(userPrincipal.getUsername());
	  ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt).path("/").maxAge(24 * 60 * 60).httpOnly(true).build();
	  return cookie;
  }
  
  public ResponseCookie getCleanJwtCookie() {
	    ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/").build();
	    return cookie;
  }
  
  public String generateJwtToken(Authentication authentication) {

    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

    return Jwts.builder()
        .setSubject((userPrincipal.getUsername()))
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
  }

  public boolean validateJwtToken(String authToken) throws SignatureException {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }

    return false;
  }
  
  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	  final Claims claims = getAllClaimsFromToken(token);
	  return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
	  return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
  }

  
  public String getUsernameFromToken(String token) {
	  return getClaimFromToken(token, Claims::getSubject);
  }

  
  public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
  }

  
  private Boolean isTokenExpired(String token) {
	  final Date expiration = getExpirationDateFromToken(token);
	  return expiration.before(new Date());
  }
  
  public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

  
  public String generateTokenFromUsername(String username) {   
	    return Jwts.builder()
	        .setSubject(username)
	        .setIssuedAt(new Date())
	        .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
	        .signWith(SignatureAlgorithm.HS512, jwtSecret)
	        .compact();
	  }
  
}
