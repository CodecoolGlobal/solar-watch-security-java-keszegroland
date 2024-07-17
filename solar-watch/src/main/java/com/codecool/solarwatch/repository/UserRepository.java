package com.codecool.solarwatch.repository;

import com.codecool.solarwatch.model.Client;


import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static java.lang.String.format;

@Repository
public class UserRepository {
    private final ConcurrentMap<String, Client> users = new ConcurrentHashMap<>();

    public synchronized Optional<Client> findByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    public synchronized void createUser(Client user) {
        String username = user.username();
        if (users.containsKey(username)) {
            throw new IllegalArgumentException(format("user %s already exists", username));
        }
        users.put(username, user);
    }

}
