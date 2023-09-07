package com.project.bookstudy.studygroup.service;

import com.project.bookstudy.studygroup.domain.param.CreateStudyGroupParam;
import com.project.bookstudy.studygroup.domain.param.UpdateStudyGroupParam;
import com.project.bookstudy.studygroup.dto.StudyGroupDto;
import com.project.bookstudy.studygroup.dto.request.StudyGroupSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudyGroupService {

    /**
     * 스터디그룹 생성
     */
    StudyGroupDto createStudyGroup(Long memberId, CreateStudyGroupParam studyGroupParam);

    /**
     * 스터디그룹 수정
     */
    void updateStudyGroup(UpdateStudyGroupParam updateParam);


    /**
     * 스터디 그룹 목록 전체 조회
     */
    Page<StudyGroupDto> getStudyGroupList(Pageable pageable, StudyGroupSearchCond cond);

    /**
     * 스터디 그룹 단일 조회
     */
    StudyGroupDto getStudyGroup(Long studyGroupId);


}
