package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.Client;
import com.codecool.solarwatch.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Client findCurrentUser() {
        UserDetails contextUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = contextUser.getUsername();
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
