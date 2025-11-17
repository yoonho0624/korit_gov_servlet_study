package com.korit.korit_gov_servlet_study.ch08.User.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Integer userId;
    private String username;
    private String password;
    private int age;
    private LocalDateTime createDt;
}
