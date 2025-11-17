package com.korit.korit_gov_servlet_study.ch08.User.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApiRespDto<T> {
    private String status;
    private String message;
    private T body;
}
