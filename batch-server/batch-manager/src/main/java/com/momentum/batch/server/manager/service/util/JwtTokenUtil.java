package com.momentum.batch.server.manager.service.util;

import com.momentum.batch.server.database.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Class description.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.5-RELEASE
 * @since 0.0.1
 */
@Component
public class JwtTokenUtil implements Serializable {

    /**
     * Token expiration time in seconds (8h)
     */
    public static final long JWT_TOKEN_VALIDITY = 8 * 60 * 60;
    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${mbm.server.jwt.secret}")
    private String secret;

    /**
     * Retrieve username from jwt token
     *
     * @param token JWT token.
     * @return user name.
     */
    public String getUsernameFromToken(String token) {
        String[] subjects = getClaimFromToken(token, Claims::getSubject).split(":");
        return subjects[0];
    }

    /**
     * Retrieve expiration date from jwt token
     *
     * @param token JWT token.
     * @return expiration date.
     */
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // For retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // Check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // Generate token for user
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, user.getUserId());
    }

    /**
     * While creating the token
     * <ul>
     * <li>Define  claims of the token, like Issuer, Expiration, Subject, and the ID.</li>
     * <li>Sign the JWT using the HS512 algorithm and secret key.</li>
     * <li>According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1) compaction of the JWT to a URL-safe string.</li>
     * </ul>
     *
     * @param claims  token claims.
     * @param subject token subject.
     * @return generated token.
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Validate token
     *
     * @param token       web token
     * @param userDetails user details
     * @return true if user could be validated, otherwise false.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        if (userDetails == null) {
            return false;
        }
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}