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
    @DisplayName("특강 신청 성공")
    void applyForLecture_success() {
        // Given
        String userId = "U001";

        LectureSession lectureSession = LectureSession.builder()
                .lectureId("L001")
                .sessionId("S001")
                .sessionDatetime(LocalDateTime.now().plusDays(3))
                .applicantsCount(0)
                .maxCapacity(30)
                .build();

        // When
        LectureApplicant applicant = lectureSession.applyForLecture(userId);

        // Then
        assertEquals(userId, applicant.getUserId());
        assertEquals(lectureSession.getSessionId(), applicant.getSessionId());
    }

    @Test
    @DisplayName("이미 지난 특강 세션인 경우, 실패")
    void applyForLecture_throwsException_whenLectureSessionIsInThePast() {
        // Given
        String userId = "U001";
        LectureSession lectureSession = LectureSession.builder()
                .lectureId("L001")
                .sessionId("S001")
                .sessionDatetime(LocalDateTime.now().minusDays(1))
                .maxCapacity(30)
                .build();

        // When & Then
        assertThrows(LectureException.class, () -> lectureSession.applyForLecture(userId));
    }

    @Test
    @DisplayName("인원이 가득찬 특강 세션인 경우, 실패")
    void applyForLecture_throwsException_whenMaxCapacityReached() {
        // Given
        String userId = "U001";

        LectureSession lectureSession = LectureSession
                .builder()
                .lectureId("L001")
                .sessionId("S001")
                .sessionDatetime(LocalDateTime.now().plusDays(3))
                .applicantsCount(30)
                .maxCapacity(30)
                .build();

        // When & Then
        assertThrows(LectureException.class, () -> lectureSession.applyForLecture(userId));
    }
}