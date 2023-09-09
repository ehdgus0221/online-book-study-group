package com.project.bookstudy.studygroup.exception;

import com.project.bookstudy.member.dto.ErrorInfo;

import java.util.ArrayList;
import java.util.List;

public abstract class StudyGroupException extends RuntimeException {

    private final List<ErrorInfo> errorInfoList = new ArrayList<>();

    public StudyGroupException(String message) {
        super(message);
    }

    public abstract int getErrorCode();

    public void addErrorInfo(String field, String errorMessage) {
        errorInfoList.add(new ErrorInfo(field, errorMessage));
    }

}
