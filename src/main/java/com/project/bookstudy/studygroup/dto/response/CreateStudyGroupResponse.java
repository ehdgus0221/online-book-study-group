
package com.project.bookstudy.studygroup.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateStudyGroupResponse {

    private Long studyGroupId;
    private Long leaderId;

    @Builder
    private CreateStudyGroupResponse(Long studyGroupId, Long leaderId) {
        this.studyGroupId = studyGroupId;
        this.leaderId = leaderId;
    }
}
