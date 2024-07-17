package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.Client;
import com.codecool.solarwatch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(client.role().toString());
        return new User(client.username(), client.password(), Set.of(role));
    }

}
