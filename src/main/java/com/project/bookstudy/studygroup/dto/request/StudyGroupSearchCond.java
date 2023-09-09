package com.project.bookstudy.studygroup.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudyGroupSearchCond {

    private String leaderName;
    private String subject;
}