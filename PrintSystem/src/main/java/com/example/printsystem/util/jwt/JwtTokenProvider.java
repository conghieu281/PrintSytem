package com.example.printsystem.util.jwt;

import com.example.printsystem.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtTokenProvider {
    @Value("${com.example.jwt.secret}")
    private String JWT_SECRET;
    @Value("${com.example.jwt.expiration}")
    private Long JWT_EXPIRATION;

    public String generateToken(CustomUserDetails customUserDetails){
        Date now = new Date();
        Date dateExpired = new Date(now.getTime() + JWT_EXPIRATION);
        System.out.println(customUserDetails.getUsername());
        return Jwts.builder()
                .setSubject(customUserDetails.getUsername())
                .claim("roles", customUserDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .claim("userId", customUserDetails.getUserId())
                .setIssuedAt(now)
                .setExpiration(dateExpired)
                .signWith(SignatureAlgorithm.HS256, key())
                .compact();
    }

    private Key key() {
        return new SecretKeySpec(Decoders.BASE64.decode(JWT_SECRET), SignatureAlgorithm.HS256.getJcaName());
    }

    public String getUserNameFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(key())
                .parseClaimsJws(token).getBody();
        System.out.println(claims.getSubject());
        return claims.getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("roles", List.class);
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex){
            log.error("Invalid JWT Token");
        } catch (ExpiredJwtException ex){
            log.error("Expired JWT Token");
        } catch (UnsupportedJwtException ex){
            log.error("Unsupported JWT Token");
        } catch (IllegalArgumentException ex){
            log.error("JWT claims String is empty");
        }
        return false;
    }
}
