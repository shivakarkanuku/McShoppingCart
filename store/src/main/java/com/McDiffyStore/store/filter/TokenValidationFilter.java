package com.McDiffyStore.store.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component

public class TokenValidationFilter extends OncePerRequestFilter {



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("JWT");
            if(null!=jwt){
                String secret="lorigjjfgkljlgirolsogjgfijnvfruglnruylilltyd";

                SecretKey secretKey= Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                        Claims claims=Jwts.parser().verifyWith(secretKey).build()
                                .parseSignedClaims(jwt).getPayload();
                        String username= String.valueOf(claims.get("username"));
                        String authorities=String.valueOf(claims.get("authorities"));
                        Authentication authentication=new UsernamePasswordAuthenticationToken(username,null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request,response);

            }
            else{
                throw new BadCredentialsException("Invalid Token");
            }

    }
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {


        return request.getServletPath().startsWith("/api/public");
    }


}
