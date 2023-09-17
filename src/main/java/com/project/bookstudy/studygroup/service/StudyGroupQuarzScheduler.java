package com.project.bookstudy.studygroup.service;

import com.project.bookstudy.studygroup.domain.StudyGroup;
import com.project.bookstudy.studygroup.domain.StudyGroupStatus;
import com.project.bookstudy.studygroup.repository.StudyGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@EnableScheduling
@RequiredArgsConstructor
public class StudyGroupQuarzScheduler {
    /**
     * 스케쥴러 Cron
     * 스터디 모집 기간, 스터디 진행 기간에 따른 스터디그룹 상태 업데이트
     * 매일 자정 (0시)에 진행
     */
    private final StudyGroupRepository studyGroupRepository;

    @Scheduled(cron = "* * 0 * * *")
    @Transactional
    public void checkStudyGroupStatus() {
        List<StudyGroup> studyGroupList = studyGroupRepository.findAll();

        for (StudyGroup studygroup : studyGroupList) {
            if (!studygroup.getStatus().equals(StudyGroupStatus.STUDY_END)) {
                if (studygroup.isStudyEnded()) {
                    studygroup.studyEnd();
                    log.info(studygroup.getId() + " : 스터디 종료");
                } else if (studygroup.isRecruitmentWaited()) {
                    studygroup.recruitWait();
                    log.info(studygroup.getId() + " : 모집 대기");
                } else if (studygroup.isRecruitmentStarted()) {
                    studygroup.recruitIng();
                    log.info(studygroup.getId() + " : 모집 중");
                } else if (studygroup.isRecruitmentEnded()) {
                    studygroup.recruitmentEnd();
                    log.info(studygroup.getId() + " : 모집 마감");
                } else if (studygroup.isStudyStarted()) {
                    studygroup.studyIng();
                    log.info(studygroup.getId() + " : 스터디 진행중");
                }
            }
        }
    }
}