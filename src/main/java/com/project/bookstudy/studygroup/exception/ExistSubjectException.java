package com.project.bookstudy.studygroup.exception;

import com.project.bookstudy.common.dto.ErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExistSubjectException extends RuntimeException{
    private ErrorCode errorCode;
    private String errorMessage;

    public ExistSubjectException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
