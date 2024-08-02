package com.codecool.solarwatch.dto;

import com.codecool.solarwatch.model.entity.Role;

public record ClientDTO(String username, String password, Role role) {
}
