package vn.hoangdung.projectJava.services;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import vn.hoangdung.projectJava.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
    
    private final JwtConfig jwtConfig;
    private final Key key;

    public JwtService(JwtConfig jwtConfig, Key key) {
        this.jwtConfig = jwtConfig;
        this.key = key;  // Đúng cách: sử dụng key được inject từ @Bean trong JwtConfig
    }

    public String generateToken(long userId, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtConfig.getExpirationTime());

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
