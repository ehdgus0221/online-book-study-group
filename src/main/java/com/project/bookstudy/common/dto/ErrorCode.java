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
    SUBJECT_ALREADY_EXISTS("동일한 제목이 이미 존재합니다."),
    STUDY_GROUP_NOT_FOUND("해당 스터디 그룹이 존재하지 않습니다."),
    STUDY_GROUP_FULL("스터디 그룹 인원이 다 찼습니다."),
    STUDY_GROUP_DELETE_FAIL("스터디 그룹 삭제 실패"),
    NOT_ENOUGH_POINT("포인트가 부족합니다."),
    ENROLLMENT_NOT_FOUND("해당 신청 정보가 없습니다."),
    ENROLLMENT_CANCEL_FAIL("해당 스터디가 진행되면 신청 취소가 불가합니다."),
    REFUND_FAIL("환불 실패");


    private final String description;
}
