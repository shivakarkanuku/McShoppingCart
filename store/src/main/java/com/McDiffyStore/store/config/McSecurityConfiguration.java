package com.McDiffyStore.store.config;

import com.McDiffyStore.store.filter.TokenValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;

@Configuration
public class McSecurityConfiguration {

    @Bean
    SecurityFilterChain StoreSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(hcf->hcf.disable())
                .sessionManagement(hsm->hsm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new TokenValidationFilter(), AuthenticationFilter.class)
//                .authorizeHttpRequests(request-> request.anyRequest().permitAll());
                .authorizeHttpRequests(request->request.requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/auth/consumer/**").hasAuthority("CONSUMER")
                .requestMatchers("/api/auth/seller/**").hasAuthority("SELLER"));
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());

        return (SecurityFilterChain)http.build();
    }

    @Bean
    PasswordEncoder McAppPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
