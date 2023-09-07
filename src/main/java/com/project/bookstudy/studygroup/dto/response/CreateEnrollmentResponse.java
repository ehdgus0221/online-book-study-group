package com.project.bookstudy.studygroup.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateEnrollmentResponse {

    private Long enrollmentId;

    @Builder
    public CreateEnrollmentResponse(Long enrollmentId) {
        this.enrollmentId = enrollmentId;
    }
}
