package com.project.bookstudy.studygroup.controller;

import com.project.bookstudy.studygroup.dto.EnrollmentDto;
import com.project.bookstudy.studygroup.dto.request.CreateEnrollmentRequest;
import com.project.bookstudy.studygroup.dto.request.EnrollmentSearchCond;
import com.project.bookstudy.studygroup.dto.response.CreateEnrollmentResponse;
import com.project.bookstudy.studygroup.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/enrollment")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public CreateEnrollmentResponse createEnrollment(@RequestBody CreateEnrollmentRequest request) {

        Long enrollmentId = enrollmentService.enroll(request);

        return CreateEnrollmentResponse.builder()
                .enrollmentId(enrollmentId)
                .build();
    }


    @DeleteMapping("/{id}")
    public void cancelEnrollment(@PathVariable("id") Long enrollmentId) {
        enrollmentService.cancel(enrollmentId);
    }

    @GetMapping("/{id}")
    public EnrollmentDto getEnrollment(@PathVariable("id") Long enrollmentId) {
        //응답 정보 선별 하고싶으면, Dto에서 정보 선별 및 응답 객체 생성
        return enrollmentService.getEnrollment(enrollmentId);
    }

    @GetMapping
    public Page<EnrollmentDto> getEnrollmentList(@PageableDefault Pageable pageable,
                                                 @ModelAttribute EnrollmentSearchCond cond) {
        //응답 정보 선별 하고싶으면, Dto에서 정보 선별 및 응답 객체 생성
        return enrollmentService.getEnrollmentList(pageable, cond.getMemberId());
    }

}
