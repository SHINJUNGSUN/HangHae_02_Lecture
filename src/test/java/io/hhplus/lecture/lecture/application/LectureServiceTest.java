package io.hhplus.lecture.lecture.application;

import io.hhplus.lecture.common.LectureException;
import io.hhplus.lecture.lecture.domain.Lecture;
import io.hhplus.lecture.lecture.domain.LectureApplicant;
import io.hhplus.lecture.lecture.domain.LectureRepository;
import io.hhplus.lecture.lecture.domain.LectureSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LectureServiceTest {

    @InjectMocks
    private LectureService lectureService;

    @Mock
    private LectureRepository lectureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("특강 신청 성공")
    void applyForLecture_success() {
        // Given
        String lectureId = "L001";
        String sessionId = "S001";
        String lectureApplicationId = "LA001";
        String userId = "U002";

        LectureDto.LectureApplyRequest request = new LectureDto.LectureApplyRequest(userId, sessionId);

        Lecture lecture = Lecture.builder()
                .lectureId(lectureId)
                .title("Introduction to AI")
                .instructor("Dr. Smith")
                .description("A comprehensive overview of artificial intelligence concepts and applications.")
                .build();

        LectureSession lectureSession = LectureSession.builder()
                .lectureId(lectureId)
                .sessionId(sessionId)
                .sessionDatetime(LocalDateTime.now().plusDays(3))
                .maxCapacity(30)
                .lecture(lecture)
                .applicantsCount(0)
                .build();

        LectureApplicant newApplicant = LectureApplicant.builder()
                .lectureApplicantId(lectureApplicationId)
                .sessionId(sessionId)
                .userId(userId)
                .appliedAt(LocalDateTime.now())
                .build();

        when(lectureRepository.findLectureSession(sessionId)).thenReturn(Optional.of(lectureSession));
        when(lectureRepository.save(any(LectureApplicant.class))).thenReturn(newApplicant);

        // When
        LectureDto.LectureApplyResponse response = lectureService.applyForLecture(request);

        // Then
        assertEquals(userId, response.userId());
        assertEquals(sessionId, response.sessionId());
    }

    @Test
    @DisplayName("특정 특강 세션이 없을 경우, 실패")
    void applyForLecture_throwsException_whenLectureSessionNotFound() {
        // Given
        LectureDto.LectureApplyRequest request = new LectureDto.LectureApplyRequest("U001", "S999");

        when(lectureRepository.findLectureSession(request.sessionId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(LectureException.class, () -> lectureService.applyForLecture(request));
    }

    @Test
    @DisplayName("이미 등록한 세션인 경우, 실패")
    void applyForLecture_throwsException_alreadyApplied() {
        // Given
        LectureDto.LectureApplyRequest request = new LectureDto.LectureApplyRequest("U001", "S001");

        when(lectureRepository.existsByUserIdAndSessionId(request.userId(), request.sessionId())).thenReturn(true);

        // When & Then
        assertThrows(LectureException.class, () -> lectureService.applyForLecture(request));
    }

    @Test
    @DisplayName("날짜별 특강 조회 성공")
    void searchLectureSessions_success() {
        // Given
        LocalDate date = LocalDate.of(2024, 10, 2);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);

        Lecture lecture = Lecture.builder()
                .title("Introduction to AI")
                .instructor("Dr. Smith")
                .description("A comprehensive overview of artificial intelligence concepts and applications.")
                .build();

        LectureSession session01 = LectureSession.builder()
                .sessionId("S001")
                .lectureId("L001")
                .sessionDatetime(start)
                .lecture(lecture)
                .maxCapacity(30)
                .applicantsCount(0)
                .build();

        LectureSession session02 = LectureSession.builder()
                .sessionId("S002")
                .lectureId("L001")
                .sessionDatetime(start.plusHours(3))
                .lecture(lecture)
                .maxCapacity(30)
                .applicantsCount(0)
                .build();

        when(lectureRepository.findLectureSessionList(start, end)).thenReturn(Arrays.asList(session01, session02));

        // When
        List<LectureDto.LectureSessionInfo> result = lectureService.searchLectureSessions(date);

        // Then
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("신청 완료 특강 조회 성공")
    void searchLectureApplicants_success() {
        // Given
        String userId = "U001";

        Lecture lecture = Lecture.builder()
                .title("Introduction to AI")
                .instructor("Dr. Smith")
                .description("A comprehensive overview of artificial intelligence concepts and applications.")
                .build();

        LectureSession session01 = LectureSession.builder()
                .sessionId("S001")
                .lectureId(lecture.getLectureId())
                .sessionDatetime(LocalDateTime.now().plusDays(3))
                .lecture(lecture)
                .maxCapacity(30)
                .applicantsCount(1)
                .build();

        LectureSession session02 = LectureSession.builder()
                .sessionId("S002")
                .lectureId(lecture.getLectureId())
                .sessionDatetime(LocalDateTime.now().plusDays(4))
                .lecture(lecture)
                .maxCapacity(30)
                .applicantsCount(1)
                .build();

        LectureApplicant applicant01 = LectureApplicant.builder()
                .lectureApplicantId("LA001")
                .userId(userId)
                .sessionId(session01.getSessionId())
                .lectureSession(session01)
                .build();

        LectureApplicant applicant02 = LectureApplicant.builder()
                .lectureApplicantId("LA002")
                .userId(userId)
                .sessionId(session02.getSessionId())
                .lectureSession(session02)
                .build();

        when(lectureRepository.findLectureApplicantListByUserId(userId)).thenReturn(Arrays.asList(applicant01, applicant02));

        // When
        List<LectureDto.LectureApplicantInfo> result = lectureService.searchLectureApplicants(userId);

        // Then
        assertEquals(2, result.size());
    }
}