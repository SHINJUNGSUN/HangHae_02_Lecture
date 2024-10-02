package io.hhplus.lecture.lecture.application;

import io.hhplus.lecture.common.LectureErrors;
import io.hhplus.lecture.common.LectureException;
import io.hhplus.lecture.lecture.domain.LectureApplicant;
import io.hhplus.lecture.lecture.domain.LectureRepository;
import io.hhplus.lecture.lecture.domain.LectureSession;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureService {

    private static final Logger log = LoggerFactory.getLogger(LectureService.class);

    private final LectureRepository lectureRepository;

    public LectureDto.LectureApplyResponse applyForLecture(LectureDto.LectureApplyRequest request) {

        // 1. 특강 세션 조회
        LectureSession session = lectureRepository.findLectureSession(request.sessionId()).orElseThrow(() -> {
            log.error("존재하지 않는 특강 세션입니다.(특강 세션 ID: {})", request.sessionId());
            return new LectureException(LectureErrors.SESSION_NOT_FOUND);
        });

        // 2. 특강 세션 신청 처리
        LectureApplicant newApplicant = session.applyForLecture(request.userId());

        // 3. 새로운 신청자 정보 저장
        lectureRepository.save(newApplicant);

        return LectureDto.LectureApplyResponse.of(session, newApplicant);
    }
}
