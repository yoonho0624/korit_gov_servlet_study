package com.korit.korit_gov_servlet_study.ch08.User.Dto;

import com.korit.korit_gov_servlet_study.ch08.User.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupReqDto {
    private String username;
    private String password;
    private int age;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .age(age)
                .build();
    }
}
