package com.project.bookstudy.studygroup.controller;

import com.project.bookstudy.studygroup.dto.CreateStudyGroup;
import com.project.bookstudy.studygroup.dto.StudyGroupDto;
import com.project.bookstudy.studygroup.dto.UpdateStudyGroupRequest;
import com.project.bookstudy.studygroup.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;


    // 스터디 그룹 생성
    @PostMapping("/study-group")
    public CreateStudyGroup.Response createStudyGroup(
            @RequestBody CreateStudyGroup.Request request
    ) {
        return CreateStudyGroup.Response.from(
                studyGroupService.createStudyGroup(request));
    }

    // 스터디 그룹 전체 조회
    @GetMapping("/study-group")
    public Page<StudyGroupDto> getStudyGroupList(Pageable pageable) {
        return studyGroupService.getStudyGroupList(pageable);
    }

    // 스터디 그룹 단적 조회
    @GetMapping("/study-group/{id}")
    public StudyGroupDto getStudyGroup(@PathVariable("id") Long studyGroupId) {
        return studyGroupService.getStudyGroup(studyGroupId);
    }

    @PostMapping("/study-group/{id}")
    public void updateStudyGroup(@PathVariable("id") Long studyId
            , @RequestBody UpdateStudyGroupRequest request) {

        studyGroupService.updateStudyGroup(studyId, request.getUpdateStudyGroupParam());

    }

}
