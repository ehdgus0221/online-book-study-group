package com.project.bookstudy.studygroup.service;

import com.project.bookstudy.studygroup.dto.CreateStudyGroup;
import com.project.bookstudy.studygroup.dto.StudyGroupDto;

public interface StudyGroupService {

    /**
     * 스터디그룹 생성
     */
    StudyGroupDto createStudyGroup(CreateStudyGroup.Request request);

}
