package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.entity.ClientEntity;
import com.codecool.solarwatch.repository.ClientRepository;
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
    private final ClientRepository clientRepository;

    @Autowired
    public UserDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClientEntity client = clientRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(client.getRole().toString());
        return new User(client.getUsername(), client.getPassword(), Set.of(role));
    }

}
