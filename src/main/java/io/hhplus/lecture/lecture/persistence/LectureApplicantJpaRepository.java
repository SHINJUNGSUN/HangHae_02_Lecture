package io.hhplus.lecture.lecture.persistence;

import io.hhplus.lecture.lecture.domain.LectureApplicant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureApplicantJpaRepository extends JpaRepository<LectureApplicant, String> {
}
