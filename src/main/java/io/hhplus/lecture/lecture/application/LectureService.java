package io.hhplus.lecture.lecture.application;

import io.hhplus.lecture.common.LectureErrors;
import io.hhplus.lecture.common.LectureException;
import io.hhplus.lecture.lecture.domain.LectureApplicant;
import io.hhplus.lecture.lecture.domain.LectureRepository;
import io.hhplus.lecture.lecture.domain.LectureSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {

    private static final Logger log = LoggerFactory.getLogger(LectureService.class);

    private final LectureRepository lectureRepository;

    public LectureDto.LectureApplyResponse applyForLecture(LectureDto.LectureApplyRequest request) {

        // 1. 특강 세션 조회
        LectureSession session = lectureRepository.findLectureSession(request.sessionId()).orElseThrow(() -> {
            log.error(LectureErrors.SESSION_NOT_FOUND.getErrorMessage());
            return new LectureException(LectureErrors.SESSION_NOT_FOUND);
        });

        // 2. 특강 세션 신청 처리
        LectureApplicant newApplicant = session.applyForLecture(request.userId());

        // 3. 새로운 신청자 정보 저장
        return LectureDto.LectureApplyResponse.of(session, lectureRepository.save(newApplicant));
    }

    public List<LectureDto.LectureSessionInfo> searchLectureSessions(LocalDate date) {

        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        List<LectureSession> sessions = lectureRepository.findLectureSessionList(start, end);

        return sessions.stream()
                .map(LectureDto.LectureSessionInfo::from)
                .collect(Collectors.toList());
    }

    public List<LectureDto.LectureApplicantInfo> searchLectureApplicants(String userId) {

        List<LectureApplicant> applicants = lectureRepository.findApplicantList(userId);

        return applicants.stream()
                .map(LectureDto.LectureApplicantInfo::from)
                .collect(Collectors.toList());
    }
}
