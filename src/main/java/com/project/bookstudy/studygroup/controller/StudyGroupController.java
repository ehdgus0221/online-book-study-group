package com.project.bookstudy.studygroup.controller;

import com.project.bookstudy.studygroup.dto.CreateStudyGroup;
import com.project.bookstudy.studygroup.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class StudyGroupController {

    private final StudyGroupService studyGroupService;

    @PostMapping("/study-group")
    public CreateStudyGroup.Response createStudyGroup(
            @RequestBody @Valid CreateStudyGroup.Request request
    ) {
        return CreateStudyGroup.Response.from(
                studyGroupService.createStudyGroup(request));

    }
}
