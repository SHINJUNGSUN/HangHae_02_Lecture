package io.hhplus.lecture.lecture.domain;

import io.hhplus.lecture.common.LectureException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LectureSessionTest {

    @Test
    @DisplayName("이미 지난 특강 세션인 경우, 실패")
    void applyForLecture_throwsException_whenLectureSessionIsInThePast() {
        // Given
        String userId = "U001";
        LectureSession lectureSession = LectureSession.builder()
                .lectureId("L001")
                .sessionId("S001")
                .sessionDatetime(LocalDateTime.now().minusDays(1))
                .max_capacity(30)
                .build();

        // When & Then
        assertThrows(LectureException.class, () -> lectureSession.applyForLecture(userId));
    }

    @Test
    @DisplayName("이미 신청이 완료된 특강 세션인 경우, 실패")
    void applyForLecture_throwsException_whenAlreadyApplied() {
        // Given
        String userId = "U001";

        List<LectureApplicant> applicants = new ArrayList<>();
        applicants.add(LectureApplicant.builder()
                .lectureApplicantId("LA001")
                .sessionId("S001")
                .userId(userId)
                .appliedAt(LocalDateTime.now())
                .build());

        LectureSession lectureSession = LectureSession.builder()
                .lectureId("L001")
                .sessionId("S001")
                .sessionDatetime(LocalDateTime.now().plusDays(3))
                .applicants(applicants)
                .max_capacity(30)
                .build();

        // When & Then
        assertThrows(LectureException.class, () -> lectureSession.applyForLecture(userId));
    }

    @Test
    @DisplayName("인원이 가득찬 특강 세션인 경우, 실패")
    void applyForLecture_throwsException_whenMaxCapacityReached() {
        // Given
        String userId = "U002";

        List<LectureApplicant> applicants = new ArrayList<>();
        applicants.add(LectureApplicant
                .builder()
                .lectureApplicantId("LA001")
                .sessionId("S001")
                .userId("U001")
                .appliedAt(LocalDateTime.now())
                .build());

        LectureSession lectureSession = LectureSession
                .builder()
                .lectureId("L001")
                .sessionId("S001")
                .sessionDatetime(LocalDateTime.now().plusDays(3))
                .applicants(applicants)
                .max_capacity(1)
                .build();

        // When & Then
        assertThrows(LectureException.class, () -> lectureSession.applyForLecture(userId));
    }
}