package com.codecool.solarwatch.model;

import com.codecool.solarwatch.model.entity.Role;

public record Client(String username, String password, Role role) {
}
