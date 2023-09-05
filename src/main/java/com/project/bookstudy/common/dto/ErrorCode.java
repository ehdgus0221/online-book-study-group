package com.project.bookstudy.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND("사용자가 없습니다."),
    EMAIL_ALREADY_EXISTS("이메일이 이미 존재합니다."),
    VALIDATION_ERROR("유효성 검사 에러"),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    EXPIRATION_TOKEN("만료된 토큰입니다."),
    INCORRECT_APPROACH("올바르지 않은 접근입니다."),
    NOT_FOUND_TOKEN("DB에 존재하지 않는 Refresh 토큰입니다."),
    AUTHORIZATION_NOT_FOUND("권한이 존재하지 않습니다."),
    LOGIN_REQUIRED("로그인이 필요합니다"),
    LOGIN_FAILED("로그인에 실패하였습니다."),
    SUBJECT_ALREADY_EXISTS("동일한 제목이 이미 존재합니다.");


    private final String description;
}
