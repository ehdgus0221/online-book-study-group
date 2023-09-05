package com.project.bookstudy.studygroup.service.impl;

import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.member.exception.MemberNotFoundException;
import com.project.bookstudy.member.repository.MemberRepository;
import com.project.bookstudy.studygroup.domain.StudyGroup;
import com.project.bookstudy.studygroup.dto.CreateStudyGroup;
import com.project.bookstudy.studygroup.dto.StudyGroupDto;
import com.project.bookstudy.studygroup.repository.StudyGroupRepository;
import com.project.bookstudy.studygroup.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.project.bookstudy.common.dto.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class StudyGroupServiceImpl implements StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final MemberRepository memberRepository;

    @Override
    public StudyGroupDto createStudyGroup(CreateStudyGroup.Request request) {

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new MemberNotFoundException());

        StudyGroup studyGroup = StudyGroup.builder()
                .subject(request.getSubject())
                .contents(request.getContents())
                .contentsDetail(request.getContentsDetail())
                .studyStartDt(request.getStudyStartDt())
                .studyEndDt(request.getStudyEndDt())
                .maxSize(request.getMaxSize())
                .price(request.getPrice())
                .recruitmentStartDt(request.getRecruitmentStartDt())
                .recruitmentEndDt(request.getRecruitmentEndDt())
                .member(member)
                .build();

        StudyGroup saveStudyGroup = studyGroupRepository.save(studyGroup);

        return StudyGroupDto.fromEntity(saveStudyGroup, member);
    }

}
