package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.payload.ClientRequest;
import com.codecool.solarwatch.model.payload.CreateClientRequest;
import com.codecool.solarwatch.model.payload.JwtResponse;

import com.codecool.solarwatch.security.jwt.JwtUtils;
import com.codecool.solarwatch.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class ClientController {
    private final ClientService clientService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public ClientController(ClientService clientService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.clientService = clientService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> createUser(@RequestBody CreateClientRequest clientRequest) {
        clientService.createUser(clientRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody ClientRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles.getFirst()));
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public String me() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "Hello " + user.getUsername();
    }

    @GetMapping("/testMe")
    public String testMe() {
        return "Hello world";
    }

    @PatchMapping("/becomeAnAdmin")
    @PreAuthorize("hasRole('USER')")
    public String becomeAnAdmin() {
        return clientService.becomeAnAdminWithAUser();
    }

}
