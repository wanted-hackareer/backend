package backend.core.security;

import backend.core.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static String SECRET_KEY = "d#nbwj2r^sqlyn=%hiq-nbi9wd88*%@l%+^a5&+$w*gq*1prnz";
    private static SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String create(Member member) {
        Date expireDate = Date.from(Instant.now().plus(30, ChronoUnit.MINUTES));

        return Jwts.builder()
                .signWith(key)
                .setSubject(String.valueOf(member.getId()))
                .setIssuer("hackareer")
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .compact();
    }

    public String validateAndGetUserId(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token).getBody();

        return claims.getSubject();
    }
}
