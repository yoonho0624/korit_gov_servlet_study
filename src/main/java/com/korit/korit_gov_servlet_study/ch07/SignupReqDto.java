package com.korit.korit_gov_servlet_study.ch07;

import lombok.Data;

@Data
public class SignupReqDto {
    private String username;
    private String password;
    private String email;
    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
