package com.codecool.solarwatch.service;

import com.codecool.solarwatch.dto.ClientDTO;
import com.codecool.solarwatch.exception.UserNotFoundException;
import com.codecool.solarwatch.model.entity.ClientEntity;
import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.payload.CreateClientRequest;
import com.codecool.solarwatch.repository.ClientRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ClientDTO createUser(CreateClientRequest clientRequest) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setUsername(clientRequest.getUsername());
        clientEntity.setPassword(passwordEncoder.encode(clientRequest.getPassword()));
        clientEntity.setRole(Role.ROLE_USER);
        clientRepository.save(clientEntity);

        return new ClientDTO(clientEntity.getUsername(), clientEntity.getPassword(), clientEntity.getRole());
    }

    public String becomeAnAdminWithAUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ClientEntity client = clientRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new UserNotFoundException(user.getUsername()));
        client.setRole(Role.ROLE_ADMIN);
        clientRepository.save(client);
        return "You are a admin now." + user.getUsername();
    }
}
