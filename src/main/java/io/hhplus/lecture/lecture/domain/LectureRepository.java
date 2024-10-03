package io.hhplus.lecture.lecture.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureRepository {
    Optional<LectureSession> findLectureSession(String sessionId);
    List<LectureSession> findLectureSessionList(LocalDateTime start, LocalDateTime end);
    LectureSession save(LectureSession session);
    LectureApplicant save(LectureApplicant applicant);
    List<LectureApplicant> findLectureApplicantListByUserId(String userId);
    List<LectureApplicant> findLectureApplicantListBySessionId(String sessionId);
    boolean existsByUserIdAndSessionId(String userId, String sessionId);
}
