package com.dudek.authservice.config.security;

import com.dudek.authservice.model.security.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@PropertySource(value = {"classpath:application.yaml"})
@Component
public class JwtUtil {

    @Value("${jwt.token.signature:default}")
    private String key;

    @Value("${jwt.token.expiration:default}")
    private int expirationTime;

    private byte[] getConvertedBinaryKey(String key) {
        String base64Key = DatatypeConverter.printBase64Binary(key.getBytes());
        return DatatypeConverter.parseBase64Binary(base64Key);
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public List<GrantedAuthority> getGrantedAuthoritiesFromToken(String token) {
        return ((List<?>) getClaimFromToken(token, claims -> claims.get("roles")))
                .stream()
                .map(role -> Role.valueOf((String) role)).collect(Collectors.toCollection(ArrayList::new));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getConvertedBinaryKey(key)).build().parseClaimsJws(token).getBody();
        } catch (SignatureException | ExpiredJwtException jwtException) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token signature or token is expired! In result this token cannot be trusted.");
        }
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        List<String> roles = userDetails.getAuthorities()
                .stream()
                .filter(authority -> authority instanceof Role)
                .map(authority -> ((Role) authority).name())
                .collect(Collectors.toList());

        claims.put("roles", roles);
        return doGenerateToken(claims, userDetails.getUsername());
    }

    public String doGenerateToken(Map<String, Object> claims, String subject) {
        Instant timeNow = Instant.now();
        SecretKey secretKey = Keys.hmacShaKeyFor(getConvertedBinaryKey(key));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(timeNow))
                .setExpiration(Date.from(timeNow.plus(expirationTime, ChronoUnit.MINUTES)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}

