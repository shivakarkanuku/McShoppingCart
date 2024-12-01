package com.McDiffyStore.store.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    public String getCurrentUserUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
