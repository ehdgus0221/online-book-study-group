package com.project.bookstudy.studygroup.controller;

import com.project.bookstudy.studygroup.dto.request.CreateStudyGroupRequest;
import com.project.bookstudy.studygroup.dto.response.CreateStudyGroupResponse;
import com.project.bookstudy.studygroup.dto.StudyGroupDto;
import com.project.bookstudy.studygroup.dto.request.UpdateStudyGroupRequest;
import com.project.bookstudy.studygroup.dto.request.StudyGroupSearchCond;
import com.project.bookstudy.studygroup.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/study-group")
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    // 스터디 그룹 생성
    @PostMapping
    public CreateStudyGroupResponse createStudyGroup (@RequestBody CreateStudyGroupRequest request) {
        StudyGroupDto studyGroupDto = studyGroupService
                .createStudyGroup(request.getMemberId(), request.toStudyGroupParam());

        return CreateStudyGroupResponse.builder()
                .studyGroupId(studyGroupDto.getId())
                .leaderId(studyGroupDto.getLeaderId())
                .build();
    }

    @GetMapping
    public Page<StudyGroupDto> getStudyGroupList (@PageableDefault Pageable pageable,
                                                 @ModelAttribute StudyGroupSearchCond cond) {
        return studyGroupService.getStudyGroupList(pageable, cond);
    }

    @GetMapping("/{id}")
    public StudyGroupDto getStudyGroup (@PathVariable("id") Long studyGroupId) {
        return studyGroupService.getStudyGroup(studyGroupId);
    }

    @PutMapping
    public void updateStudyGroup (@RequestBody UpdateStudyGroupRequest request) {
        studyGroupService.updateStudyGroup(request.toUpdateStudyGroupParam());
    }

    @PatchMapping("/{id}")
    public void cancelStudyGroup(@PathVariable("id") Long studyId) {
        studyGroupService.cancelStudyGroup(studyId);
    }


}
