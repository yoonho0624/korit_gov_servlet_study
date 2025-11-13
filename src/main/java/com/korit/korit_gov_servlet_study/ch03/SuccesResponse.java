package com.korit.korit_gov_servlet_study.ch03;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccesResponse<T> {
    private int status = 200;
    private String message;
    private T body;
}
