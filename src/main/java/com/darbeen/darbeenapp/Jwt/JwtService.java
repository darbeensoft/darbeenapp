package com.darbeen.darbeenapp.Jwt;

import com.darbeen.darbeenapp.User.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    //clave secreta para generar y verificar el token
    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";



    //crea un token para un usuario
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }
    //crea un token con claims adicionales
    private String getToken(Map<String,Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    //decodifica la key del token
    private static Key getKey() {//decodificar la key
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);//crea nueva instancia de la secret key
    }
    //obtiene el nombre del usuario a partir del token
    public static String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }
    //verifica si el token es válido
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }
    //obtiene todos los claims del token
    private static Claims getAllClaims(String token)
    {
        return Jwts
               .parserBuilder()
               .setSigningKey(getKey())
               .build()
               .parseClaimsJws(token)
               .getBody();
    }
    //obtiene un claim del token
    public static <T> T getClaim(String token, Function<Claims, T> claimResolver)
    {
        final Claims claims =getAllClaims(token);
        return claimResolver.apply(claims);

    }
    //obtiene la fecha de expiración del token
    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }
    //verifica si expiro el token
    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }
}
