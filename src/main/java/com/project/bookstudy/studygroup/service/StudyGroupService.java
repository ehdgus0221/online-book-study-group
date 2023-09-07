package com.project.bookstudy.studygroup.service;

import com.project.bookstudy.studygroup.domain.UpdateStudyGroupParam;
import com.project.bookstudy.studygroup.dto.CreateStudyGroup;
import com.project.bookstudy.studygroup.dto.StudyGroupDto;
import com.project.bookstudy.studygroup.dto.UpdateStudyGroupRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudyGroupService {

    /**
     * 스터디그룹 생성
     */
    StudyGroupDto createStudyGroup(CreateStudyGroup.Request request);

    /**
     * 스터디그룹 수정
     */
    void updateStudyGroup(long id, UpdateStudyGroupParam updateParam);


    /**
     * 스터디 그룹 목록 전체 조회
     */
    Page<StudyGroupDto> getStudyGroupList(Pageable pageable);

    /**
     * 스터디 그룹 단일 조회
     */
    StudyGroupDto getStudyGroup(Long studyGroupId);
}
