package com.room.manage.core.security;

import com.room.manage.api.user.model.entity.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
     * 하루
     */
    @Value("${time.accessToken.expire}")
    Long accessTokenExpire;

    /**
     * 한달
     */
    @Value("${time.refreshToken.expire}")
    Long refreshTokenExpire;

    public String getTokenFromHeader(HttpServletRequest request)
    {
        String token = request.getHeader("Authorization");
        return token.contains("Bearer")?token.replace("Bearer ",""):null;
    }

    public boolean validateToken(String token)
    {
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date());
        }catch (Exception e){
             return false;
        }
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

    public String generateRefreshToken(Long userId, UserRole role) {
        Date expire = new Date(new Date().getTime() + refreshTokenExpire);
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
        return new UsernamePasswordAuthenticationToken(getUserIdFromToken(token),"",List.of(getAuthorities(getClaimsFromToken(token))));
    }
}
