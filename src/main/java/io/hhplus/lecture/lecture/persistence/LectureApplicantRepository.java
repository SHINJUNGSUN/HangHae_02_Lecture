package io.hhplus.lecture.lecture.persistence;

import io.hhplus.lecture.lecture.domain.LectureApplicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureApplicantRepository extends JpaRepository<LectureApplicant, String> {
    List<LectureApplicant> findByUserId(String userId);
    List<LectureApplicant> findBySessionId(String sessionId);
    boolean existsByUserIdAndSessionId(String userId, String sessionId);
}
