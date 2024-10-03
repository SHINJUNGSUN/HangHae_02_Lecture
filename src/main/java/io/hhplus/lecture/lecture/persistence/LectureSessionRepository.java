package io.hhplus.lecture.lecture.persistence;

import io.hhplus.lecture.lecture.domain.LectureSession;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LectureSessionRepository extends JpaRepository<LectureSession, String> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("select ls from LectureSession ls where ls.sessionId = :sessionId")
    Optional<LectureSession> findLectureSessionBySessionId(String sessionId);
    List<LectureSession> findBySessionDatetimeBetween(LocalDateTime start, LocalDateTime end);
}
