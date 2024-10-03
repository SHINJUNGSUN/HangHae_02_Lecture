package io.hhplus.lecture.lecture.persistence;

import io.hhplus.lecture.lecture.domain.LectureApplicant;
import io.hhplus.lecture.lecture.domain.LectureRepository;
import io.hhplus.lecture.lecture.domain.LectureSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureSessionRepository lectureSessionRepository;
    private final LectureApplicantRepository lectureApplicantRepository;

    @Override
    public Optional<LectureSession> findLectureSession(String sessionId) {
        return lectureSessionRepository.findLectureSessionBySessionId(sessionId);
    }

    @Override
    public List<LectureSession> findLectureSessionList(LocalDateTime start, LocalDateTime end) {
        return lectureSessionRepository.findBySessionDatetimeBetween(start, end);
    }

    @Override
    public LectureSession save(LectureSession session) {
        return lectureSessionRepository.save(session);
    }

    @Override
    public LectureApplicant save(LectureApplicant applicant) {
        return lectureApplicantRepository.save(applicant);
    }

    @Override
    public List<LectureApplicant> findLectureApplicantListByUserId(String userId) {
        return lectureApplicantRepository.findByUserId(userId);
    }

    @Override
    public List<LectureApplicant> findLectureApplicantListBySessionId(String sessionId) {
        return lectureApplicantRepository.findBySessionId(sessionId);
    }
}