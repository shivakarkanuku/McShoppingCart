package com.McDiffyStore.store.config;

import com.McDiffyStore.store.entity.McUser;
import com.McDiffyStore.store.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class McAppUserDetailsManager implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        McUser mcUser=usersRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found for the user :"+username));
        List<GrantedAuthority> authorities=mcUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole())).collect(Collectors.toList());
        return new User(mcUser.getUsername(),mcUser.getPassword(),authorities);
    }
}
