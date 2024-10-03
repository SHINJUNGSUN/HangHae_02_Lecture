package io.hhplus.lecture.lecture.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

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
}
