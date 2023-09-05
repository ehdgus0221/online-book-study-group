package com.project.bookstudy.security.exception;

import com.project.bookstudy.common.dto.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public TokenException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
