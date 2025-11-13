package com.korit.korit_gov_servlet_study.ch03;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private String name;
    private String email;
    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .name(name)
                .email(email)
                .build();
    }
}
