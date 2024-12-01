package com.McDiffyStore.store.jwt;

import com.McDiffyStore.store.dto.LoginDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtTokenGenerator {

    @Autowired
    AuthenticationProvider authenticationProvider;
    public ResponseEntity<String> generateToken(LoginDto loginDto) {
        Authentication preAuthentication=new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        Authentication authenticatedObject=authenticationProvider.authenticate(preAuthentication);
        if(authenticatedObject.isAuthenticated()){
            String secret="lorigjjfgkljlgirolsogjgfijnvfruglnruylilltyd";
            SecretKey secretKey= Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            String jwt= Jwts.builder().issuer("McShoppingPvtLtd").subject("jwt_token")
                    .claim("username",authenticatedObject.getName())
                    .claim("authorities",authenticatedObject.getAuthorities()
                            .stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime()+30000000))
                    .signWith(secretKey).compact();

            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }

        return new ResponseEntity<>("invalid credentials",HttpStatus.UNAUTHORIZED);
    }
}
