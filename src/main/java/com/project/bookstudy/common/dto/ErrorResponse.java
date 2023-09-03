package com.project.bookstudy.common.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
public class ErrorResponse {

    private String code;
    private String message;
    private String errorDetail;

    @Builder
    private ErrorResponse(String code, String message, String errorDetail) {
        this.code = code;
        this.message = message;
        this.errorDetail = errorDetail;
    }

}
