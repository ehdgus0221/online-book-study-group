package com.project.bookstudy.impl;

import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.member.repository.MemberRepository;
import com.project.bookstudy.studygroup.domain.StudyGroup;
import com.project.bookstudy.studygroup.dto.CreateStudyGroup;
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

}