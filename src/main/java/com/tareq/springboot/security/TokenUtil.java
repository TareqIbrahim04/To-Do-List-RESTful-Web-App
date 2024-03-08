package com.tareq.springboot.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    private final String CLAIMS_SUBJECT = "sub";
    private final String CLAIMS_CREATED = "created";
    @Value("${auth.expiration}") // @Value annotation let me fetch the value of the attribute from application.yml
    private Long TOKEN_VALIDITY; // 1 week in seconds
    @Value("${auth.secret}")
    private String TOKEN_SECRET;

    public String generateToken(UserDetails userDetails){
        // we should pass claims, expiration, sign, compact //(claims , expiration, sign) belong to JWT, compact to invert them to String
        Map<String, Object> claims = new HashMap<>();

        claims.put(CLAIMS_SUBJECT, userDetails.getUsername());
        claims.put(CLAIMS_CREATED, new Date());
        return Jwts.builder()
                .setClaims(claims) // Jwt token attributes
                .setExpiration(generateExpirationDate()) // expiration date for the token
                .signWith(SignatureAlgorithm.HS512, TextCodec.BASE64.decode(TOKEN_SECRET)) // algorithm used for jwt key, key
                .compact();
    }
    public String getUserNameFromToken(String token){
        try{
            Claims claims = getClaims(token);
            return claims.getSubject(); // return subject (part of claims) which means email in my project
        }catch (Exception ex){
            return null;
        }
    }
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + TOKEN_VALIDITY*1000);
    }

    public Claims getClaims(String token){ // get claims from token
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token).getBody();
        }
        catch (Exception ex){
            claims = null;
        }
        return claims;
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    public boolean isTokenExpired(String token) {
        Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}
