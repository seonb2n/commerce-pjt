package com.example.commercepjt.dto.request;

import com.example.commercepjt.common.enums.RoleStatus;

public record UserRegisterDto(String username,
                              String password, RoleStatus roleStatus) {

}
