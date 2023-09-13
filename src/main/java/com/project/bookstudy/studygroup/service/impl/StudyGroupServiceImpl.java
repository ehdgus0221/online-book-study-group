package com.project.bookstudy.studygroup.service.impl;

import com.project.bookstudy.common.dto.ErrorCode;
import com.project.bookstudy.member.domain.Member;
import com.project.bookstudy.member.repository.MemberRepository;
import com.project.bookstudy.studygroup.domain.StudyGroup;
import com.project.bookstudy.studygroup.domain.param.CreateStudyGroupParam;
import com.project.bookstudy.studygroup.domain.param.UpdateStudyGroupParam;
import com.project.bookstudy.studygroup.dto.StudyGroupDto;
import com.project.bookstudy.studygroup.dto.request.StudyGroupSearchCond;
import com.project.bookstudy.studygroup.repository.EnrollmentRepository;
import com.project.bookstudy.studygroup.repository.StudyGroupRepository;
import com.project.bookstudy.studygroup.service.StudyGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StudyGroupServiceImpl implements StudyGroupService {

    private final StudyGroupRepository studyGroupRepository;
    private final MemberRepository memberRepository;
    private final EnrollmentRepository enrollmentRepository;


    @Override
    @Transactional
    public StudyGroupDto createStudyGroup(Long memberId, CreateStudyGroupParam studyGroupParam) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getDescription()));

        StudyGroup savedStudyGroup = studyGroupRepository.save(StudyGroup.from(member, studyGroupParam));

        return StudyGroupDto.fromEntity(savedStudyGroup);
    }

    @Override
    @Transactional
    public void updateStudyGroup(UpdateStudyGroupParam updateParam) {

        StudyGroup studyGroup = studyGroupRepository.findById(updateParam.getId())
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.STUDY_GROUP_NOT_FOUND.getDescription()));

        studyGroup.update(updateParam);
    }


    @Override
    public Page<StudyGroupDto> getStudyGroupList(Pageable pageable, StudyGroupSearchCond cond) {

        Page<StudyGroup> studyGroups = studyGroupRepository.searchStudyGroup(pageable, cond);

        return studyGroups.map(entity -> StudyGroupDto.fromEntity(entity));
    }

    @Override
    public StudyGroupDto getStudyGroup(Long studyGroupId) {

        StudyGroup studyGroup = studyGroupRepository.findByIdWithLeader(studyGroupId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.STUDY_GROUP_NOT_FOUND.getDescription()));

        return StudyGroupDto.fromEntity(studyGroup);
    }

    @Override
    @Transactional
    public void cancelStudyGroup(Long id) {
        StudyGroup studyGroup = studyGroupRepository.findByIdWithEnrollmentWithAll(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.USER_NOT_FOUND.getDescription()));

        if (studyGroup.isStarted()) {
            throw new IllegalStateException(ErrorCode.STUDY_GROUP_CANCEL_FAIL.getDescription());
        }

        studyGroup.cancel();
    }

}
