package io.hhplus.lecture.lecture.persistence;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.hhplus.lecture.lecture.domain.LectureApplicant;
import io.hhplus.lecture.lecture.domain.LectureRepository;
import io.hhplus.lecture.lecture.domain.LectureSession;
import io.hhplus.lecture.lecture.domain.QLectureSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final LectureApplicantRepository lectureApplicantRepository;

    private final QLectureSession lectureSession = QLectureSession.lectureSession;

    @Override
    public Optional<LectureSession> findLectureSession(String sessionId) {
        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(lectureSession)
                .where(lectureSession.sessionId.eq(sessionId))
                .fetchOne());
    }

    @Override
    public List<LectureSession> findLectureSessionList(LocalDateTime start, LocalDateTime end) {
        return jpaQueryFactory
                .selectFrom(lectureSession)
                .where(lectureSession.sessionDatetime.between(start, end))
                .fetch();
    }

    @Override
    public LectureApplicant save(LectureApplicant applicant) {
        return lectureApplicantRepository.save(applicant);
    }

    @Override
    public List<LectureApplicant> findApplicantList(String userId) {
        return lectureApplicantRepository.findByUserId(userId);
    }
}