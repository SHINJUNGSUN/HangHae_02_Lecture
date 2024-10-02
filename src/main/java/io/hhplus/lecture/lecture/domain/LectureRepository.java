package io.hhplus.lecture.lecture.domain;

import java.util.Optional;

public interface LectureRepository {
    Optional<LectureSession> findLectureSession(String sessionId);
    LectureApplicant save(LectureApplicant applicant);
}
