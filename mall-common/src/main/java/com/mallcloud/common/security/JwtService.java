package com.mallcloud.common.security;

import io.jsonwebtoken.Claims; import io.jsonwebtoken.Jwts; import io.jsonwebtoken.io.Decoders; import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.EnableConfigurationProperties; import org.springframework.stereotype.Component;
import javax.crypto.SecretKey; import java.time.Instant; import java.util.Date;

@Component
@EnableConfigurationProperties(JwtProperties.class)
public class JwtService {
    private final JwtProperties properties; private final SecretKey key;
    public JwtService(JwtProperties properties) { this.properties = properties; this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(properties.getSecret())); }
    public String createToken(AuthenticatedUser user) {
        Instant now = Instant.now();
        return Jwts.builder().subject(user.userId().toString()).claim("username", user.username()).claim("role", user.role())
                .issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(properties.getExpirationSeconds()))).signWith(key).compact();
    }
    public AuthenticatedUser parseToken(String token) {
        Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
        return new AuthenticatedUser(Long.valueOf(claims.getSubject()), claims.get("username", String.class), claims.get("role", String.class));
    }
    public long expiresIn() { return properties.getExpirationSeconds(); }
}
