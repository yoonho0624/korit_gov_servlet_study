package com.korit.korit_gov_servlet_study.ch06;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SuccesResponse {
    private String message;
}
