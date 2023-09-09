package com.project.bookstudy.studygroup.exception;

import com.project.bookstudy.member.exception.MemberException;

public class StudyGroupNotFoundException extends MemberException {

    public final static String MESSAGE = "존재하지 않는 스터디그룹입니다.";

    public StudyGroupNotFoundException() {
        super(MESSAGE);
    }

    public StudyGroupNotFoundException(String field, String errorMessage) {
        super(MESSAGE);
        addErrorInfo(field, errorMessage);
    }

    @Override
    public int getErrorCode() {
        return 400;
    }

    @Override
    public void addErrorInfo(String field, String errorMessage) {
        super.addErrorInfo(field, errorMessage);
    }
}
