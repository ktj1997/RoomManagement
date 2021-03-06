package com.room.manage.core.security;

import com.room.manage.api.auth.exception.NotBearerTokenException;
import com.room.manage.api.user.model.entity.UserRole;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.codec.DecodingException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${application.jwt.secret}")
    String jwtSecret;
    /**
     * 한달
     */
    @Value("${time.accessToken.expire}")
    Long accessTokenExpire;

    public String parsingToken(String token)
    {
        if(token.startsWith("Bearer "))
            return token.replace("Bearer ","");
        else
           throw new NotBearerTokenException();
    }


    public boolean validateToken(String token){
        Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(parsingToken(token));
        return claims.getBody().getExpiration().after(new Date());

    }
    public String generateAccessToken(Long userId, UserRole role) {
        Date expire = new Date(new Date().getTime() + accessTokenExpire);
        return Jwts.builder()
                .claim("id",userId)
                .claim("roles",role)
                .setIssuedAt(new Date())
                .setExpiration(expire)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }
    public Long getUserIdFromToken(String token)
    {
        return getClaimsFromToken(token).get("id",Long.class);
    }
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
    public GrantedAuthority getAuthorities(Claims claims)
    {
        String role = claims.get("roles",String.class);
        return new SimpleGrantedAuthority(role);
    }
    public Authentication getAuthentication(String token)
    {
        UsernamePasswordAuthenticationToken authentication = null;
        if(token != null){
            String parsingToken = parsingToken(token);
            if(validateToken(token))
               authentication =  new UsernamePasswordAuthenticationToken(getUserIdFromToken(parsingToken),"",List.of(getAuthorities(getClaimsFromToken(parsingToken))));
        }
        return authentication;
    }
}
