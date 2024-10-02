package io.hhplus.lecture.lecture.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureRepository {
    Optional<LectureSession> findLectureSession(String sessionId);
    List<LectureSession> findLectureSessionList(LocalDateTime start, LocalDateTime end);
    LectureApplicant save(LectureApplicant applicant);
    List<LectureApplicant> findApplicantList(String userId);
}
