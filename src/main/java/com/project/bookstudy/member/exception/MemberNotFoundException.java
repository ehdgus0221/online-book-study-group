package com.project.bookstudy.member.exception;

public class MemberNotFoundException extends MemberException{

    public final static String MESSAGE = "존재하지 않는 회원입니다.";

    public MemberNotFoundException() {
        super(MESSAGE);
    }

    public MemberNotFoundException(String field, String errorMessage) {
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
