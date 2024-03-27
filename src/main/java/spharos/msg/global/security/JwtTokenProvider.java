package spharos.msg.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import spharos.msg.global.api.code.status.ErrorStatus;
import spharos.msg.global.api.exception.JwtTokenException;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final Environment env;
    public static final String BEARER = "Bearer";

    /**
     * "Bearer " + token, "Bearer " 삭제 후, uuid 반환하게 됩니다.
     * Bearer 없을 시, JwtTokenException Exception 호출
     * @param "Bearer " + token
     * @return Token's Uuid
     */
    public String getUuid(String token) {
        if (!token.startsWith(BEARER)) {
            throw new JwtTokenException(ErrorStatus.TOKEN_VALIDATION_ERROR);
        }
        return extractClaim(token.substring(7), Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserDetails userDetails, long expiration, String type) {
        return generateToken(Map.of(), userDetails, expiration, type);
    }

    public String generateToken(
        Map<String, Object> extractClaims,
        UserDetails userDetails,
        long expiration,
        String type
    ) {

        return Jwts.builder()
            .setClaims(extractClaims)
            .claim("token_type", type)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new java.util.Date(System.currentTimeMillis()))
            .setExpiration(new java.util.Date(System.currentTimeMillis() + expiration))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String validateAndGetUserUuid(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String getUsersUUID(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (NullPointerException e) {
            return null;
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String uuid = getUuid(token);
        return (uuid.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new java.util.Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(env.getProperty("JWT.SECRET_KEY"));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}