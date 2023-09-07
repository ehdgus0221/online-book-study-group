package com.project.bookstudy.studygroup.service.impl;

import com.project.bookstudy.common.dto.ErrorCode;
import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.member.exception.MemberNotFoundException;
import com.project.bookstudy.member.repository.MemberRepository;
import com.project.bookstudy.studygroup.domain.StudyGroup;
import com.project.bookstudy.studygroup.domain.UpdateStudyGroupParam;
import com.project.bookstudy.studygroup.dto.CreateStudyGroup;
import com.project.bookstudy.studygroup.dto.StudyGroupDto;
import com.project.bookstudy.studygroup.dto.UpdateStudyGroupRequest;
import com.project.bookstudy.studygroup.exception.StudyGroupNotFoundException;
import com.project.bookstudy.studygroup.repository.StudyGroupRepository;
import com.project.bookstudy.studygroup.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .leader(member)
                .build();

        StudyGroup saveStudyGroup = studyGroupRepository.save(studyGroup);

        return StudyGroupDto.fromEntity(saveStudyGroup, member);
    }

    @Override
    public void updateStudyGroup(long id, UpdateStudyGroupParam updateParam) {
        StudyGroup studyGroup = studyGroupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.STUDY_GROUP_NOT_FOUND.getDescription()));

        studyGroup.update(updateParam);
    }


    @Override
    public Page<StudyGroupDto> getStudyGroupList(Pageable pageable) {
        Page<StudyGroup> studyGroups = studyGroupRepository.searchStudyGroup(pageable);
        return studyGroups.map(entity -> StudyGroupDto.fromEntity(entity, entity.getLeader()));
    }

    @Override
    public StudyGroupDto getStudyGroup(Long studyGroupId) {

        StudyGroup studyGroup = studyGroupRepository.findByIdWithLeader(studyGroupId);
        return StudyGroupDto.fromEntity(studyGroup, studyGroup.getLeader());
    }


}
