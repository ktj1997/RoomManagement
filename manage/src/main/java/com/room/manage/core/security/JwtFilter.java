package com.room.manage.core.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.room.manage.api.auth.exception.NotBearerTokenException;
import com.room.manage.core.Exception.ExceptionCode;
import com.room.manage.core.Exception.ExceptionResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthentication(request.getHeader("Authorization")));
            filterChain.doFilter(request,response);
        }catch(SignatureException e){
            sendErrorMessage(response,ExceptionCode.INVALID_TOKEN);
        }catch(MalformedJwtException e){
            sendErrorMessage(response,ExceptionCode.MALFORMED_TOKEN);
        }catch(ExpiredJwtException e){
            sendErrorMessage(response,ExceptionCode.EXPIRE_TOKEN);
        }catch(NotBearerTokenException e){
            sendErrorMessage(response,ExceptionCode.NOT_BEARER_FORMAT);
        }
    }
    private void sendErrorMessage(HttpServletResponse res, ExceptionCode code) throws IOException {
        res.setStatus(HttpServletResponse.SC_FORBIDDEN);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.getWriter().write(objectMapper.writeValueAsString(new ExceptionResponse(HttpStatus.FORBIDDEN,code)));

    }
}