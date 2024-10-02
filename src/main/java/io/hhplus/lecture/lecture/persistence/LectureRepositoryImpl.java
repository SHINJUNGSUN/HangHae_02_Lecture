package io.hhplus.lecture.lecture.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.hhplus.lecture.lecture.domain.LectureApplicant;
import io.hhplus.lecture.lecture.domain.LectureRepository;
import io.hhplus.lecture.lecture.domain.LectureSession;
import io.hhplus.lecture.lecture.domain.QLectureSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final LectureApplicantJpaRepository lectureApplicantJpaRepository;

    private final QLectureSession lectureSession = QLectureSession.lectureSession;

    @Override
    public Optional<LectureSession> findLectureSession(String sessionId) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(lectureSession)
                .where(lectureSession.sessionId.eq(sessionId))
                .fetchOne());
    }

    @Override
    public LectureApplicant save(LectureApplicant applicant) {
        return lectureApplicantJpaRepository.save(applicant);
    }
}