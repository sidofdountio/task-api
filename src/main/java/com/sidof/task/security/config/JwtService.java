package com.sidof.task.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.SignatureAlgorithm.*;

/**
 * @Author sidof
 * @Since 10/3/23
 * @Version v1.0
 * @YouTube @sidof8065
 */
@Service @RequiredArgsConstructor
public class JwtService {
    private static final String SECRET_KEY = "c41e06ea23f05da44593dac675706b89c41d5783481dc1b81221afe16aa28196";

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
    /**    generate token have too parameters:
     * extractClaim: Map;
     * user: UserDetails from spring.security.
     */
    public String generateToken(Map<String,Object> extractClaim, UserDetails userDetails){
        return Jwts
                .builder()
                .setClaims(extractClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), HS256)
                .compact();
    }

    /**
     *  Return true if username in the token equals to username in userDetails. And the expiration date.
     */
    public boolean isValidToken(String token,UserDetails userDetails){
        String username= extractUserEmail(token);
        return  username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    public String extractUserEmail(String token) {
        return extractClaim(token,Claims::getSubject);
    }
//    extractClaim
    public <T>T extractClaim (String token, Function<Claims,T> claimResolver){
    final Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
    }
    //    extract all claim from the token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keys = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keys);
    }
}
