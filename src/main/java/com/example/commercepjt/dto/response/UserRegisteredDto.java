package com.example.commercepjt.dto.response;

import com.example.commercepjt.domain.User;

public record UserRegisteredDto(
    Long userId,
    String userLoginId,
    String userName,
    String userRole
) {

    public UserRegisteredDto(User entity) {
        this(entity.getId(), entity.getUsername(), entity.getNickName(),
            entity.getRole().getRoleStatus().name());
    }

}
