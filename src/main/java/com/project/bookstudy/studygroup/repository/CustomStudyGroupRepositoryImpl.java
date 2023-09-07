package com.project.bookstudy.studygroup.repository;

import com.project.bookstudy.studygroup.domain.EnrollmentStatus;
import com.project.bookstudy.studygroup.domain.QStudyGroup;
import com.project.bookstudy.studygroup.domain.StudyGroup;
import com.project.bookstudy.studygroup.dto.request.StudyGroupSearchCond;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static com.project.bookstudy.member.domain.QMember.member;
import static com.project.bookstudy.studygroup.domain.QEnrollment.enrollment;
import static com.project.bookstudy.studygroup.domain.QStudyGroup.studyGroup;

@RequiredArgsConstructor
public class CustomStudyGroupRepositoryImpl implements CustomStudyGroupRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<StudyGroup> findByIdWithLeader(Long id) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(studyGroup)
                .where(studyGroup.id.eq(id))
                .join(studyGroup.leader, member).fetchJoin()
                .fetchOne());
    }

    @Override
    public Optional<StudyGroup> findByIdWithEnrollments(Long id) {

        StudyGroup studyGroup = jpaQueryFactory
                .selectFrom(QStudyGroup.studyGroup)
                .join(QStudyGroup.studyGroup.enrollments, enrollment).fetchJoin()
                .where(enrollment.enrollmentStatus.eq(EnrollmentStatus.RESERVED))
                .fetchOne();

        return Optional.ofNullable(studyGroup);
    }

    @Override
    public Page<StudyGroup> searchStudyGroup(Pageable pageable, StudyGroupSearchCond cond) {

        List<StudyGroup> studyGroups = jpaQueryFactory
                .selectFrom(studyGroup)
                .join(studyGroup.leader, member).fetchJoin()
                .where(leaderNameCond(cond.getLeaderName()), subjectCond(cond.getSubject()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long totalCount = jpaQueryFactory
                .select(studyGroup.count())
                .from(studyGroup)
                .fetchOne();

        return new PageImpl<StudyGroup>(studyGroups, pageable, totalCount);
    }

    private BooleanExpression leaderNameCond(String leaderName) {
        if (!StringUtils.hasText(leaderName)) {
            return null;
        }
        return member.name.contains(leaderName);
    }

    private BooleanExpression subjectCond(String subject) {
        if (!StringUtils.hasText(subject)) {
            return null;
        }
        return studyGroup.subject.contains(subject);
    }


}
