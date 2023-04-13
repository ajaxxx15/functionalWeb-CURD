package com.demo.functionalweb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppExceptionResponse {

    private String code;
    private String message;
    private Timestamp timestamp;
}