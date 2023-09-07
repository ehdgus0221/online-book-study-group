package com.project.bookstudy.impl;

import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.member.repository.MemberRepository;
import com.project.bookstudy.studygroup.domain.StudyGroup;
import com.project.bookstudy.studygroup.dto.StudyGroupDto;
import com.project.bookstudy.studygroup.repository.StudyGroupRepository;
import com.project.bookstudy.studygroup.service.impl.StudyGroupServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudyGroupServiceImplTest {

    @Autowired
    private StudyGroupRepository studyGroupRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StudyGroupServiceImpl studyGroupServiceImpl;

    @Test
    @DisplayName("스터디그룹 개설 성공 테스트")
    void createStudyGroupSuccess() {
        // Given
        Member member = Member.builder()
                .email("dlaehdgus23@naver.com")
                .build();
        memberRepository.save(member);


        CreateStudyGroup.Request request = new CreateStudyGroup.Request();

        request.setSubject("제목");
        request.setContents("스프링");
        request.setContentsDetail("스프링 스터디 그룹");
        request.setMaxSize(10);
        request.setPrice(50000);
        request.setStudyStartDt(LocalDateTime.now());
        request.setStudyEndDt(LocalDateTime.now());
        request.setRecruitmentStartDt(LocalDateTime.now());
        request.setRecruitmentEndDt(LocalDateTime.now());
        request.setMemberId(1L);

        //`"yyyy-mm-dd'T'HH:MM:SS"
        // When
        StudyGroupDto studyGroupDto = studyGroupServiceImpl.createStudyGroup(request);
        StudyGroup studyGroup = studyGroupRepository.findById(studyGroupDto.getId()).orElseThrow(() -> new IllegalArgumentException("id 없음"));


        // Then

        assertEquals(2L, studyGroup.getId());
        assertEquals(1L, request.getMemberId());
        assertEquals(request.getSubject(), studyGroup.getSubject());
        assertEquals(request.getContents(), studyGroup.getContents());
        assertEquals(request.getContentsDetail(), studyGroup.getContentsDetail());
        assertEquals(request.getMaxSize(), studyGroup.getMaxSize());
        assertEquals(request.getPrice(), studyGroup.getPrice());
        assertNotNull(studyGroup.getStudyStartDt());
        assertNotNull(studyGroup.getStudyEndDt());
        assertNotNull(studyGroup.getRecruitmentStartDt());
        assertNotNull(studyGroup.getRecruitmentEndDt());

    }

/*    @Test
    @DisplayName("스터디그룹 수정 성공 테스트")
    void updateStudyGroupSuccess() {
        // Given
        Member member = Member.builder()
                .email("dlaehdgus23@naver.com")
                .build();
        memberRepository.save(member);

        CreateStudyGroup.Request request = new CreateStudyGroup.Request();

        request.setSubject("제목");
        request.setContents("스프링");
        request.setContentsDetail("스프링 스터디 그룹");
        request.setMaxSize(10);
        request.setPrice(50000);
        request.setStudyStartDt(LocalDateTime.now());
        request.setStudyEndDt(LocalDateTime.now());
        request.setRecruitmentStartDt(LocalDateTime.now());
        request.setRecruitmentEndDt(LocalDateTime.now());
        request.setMemberId(1L);

        // When
        StudyGroupDto studyGroupDto = studyGroupServiceImpl.createStudyGroup(request);

        UpdateStudyGroupRequest.Request request2 = new UpdateStudyGroupRequest.Request();

        request2.setSubject("제목2");
        request2.setContents("스프링2");
        request2.setContentsDetail("스프링 스터디 그룹2");
        request2.setMaxSize(11);
        request2.setPrice(50001);
        request2.setStudyStartDt(LocalDateTime.now());
        request2.setStudyEndDt(LocalDateTime.now());
        request2.setRecruitmentStartDt(LocalDateTime.now());
        request2.setRecruitmentEndDt(LocalDateTime.now());
        request2.setMemberId(1L);

        //`"yyyy-mm-dd'T'HH:MM:SS"
        // When
       StudyGroupDto studyGroupDto2 = studyGroupServiceImpl.updateStudyGroup(request2,2);
        StudyGroup studyGroup = studyGroupRepository.findById(studyGroupDto.getId()).orElseThrow(() -> new IllegalArgumentException("id 없음"));


        // Then
        assertEquals(2L, studyGroup.getId());
        assertEquals(1L, request2.getMemberId());
        assertEquals(request2.getSubject(), studyGroup.getSubject());
        assertEquals(request2.getContents(), studyGroup.getContents());
        assertEquals(request2.getContentsDetail(), studyGroup.getContentsDetail());
        assertEquals(request2.getMaxSize(), studyGroup.getMaxSize());
        assertEquals(request2.getPrice(), studyGroup.getPrice());
        assertNotNull(studyGroup.getStudyStartDt());
        assertNotNull(studyGroup.getStudyEndDt());
        assertNotNull(studyGroup.getRecruitmentStartDt());
        assertNotNull(studyGroup.getRecruitmentEndDt());

    }*/



}