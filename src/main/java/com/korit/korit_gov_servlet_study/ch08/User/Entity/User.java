package com.korit.korit_gov_servlet_study.ch08.User.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    @JsonFormat(pattern = "YYYY-mm-dd kk:mm:ss")
    private LocalDateTime createDt;
}
