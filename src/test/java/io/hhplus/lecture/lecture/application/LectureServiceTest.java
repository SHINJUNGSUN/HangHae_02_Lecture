package io.hhplus.lecture.lecture.application;

import io.hhplus.lecture.common.LectureException;
import io.hhplus.lecture.lecture.domain.LectureRepository;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

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
    @DisplayName("특정 특강 세션이 없을 경우, 실패")
    void applyForLecture_throwsException_whenLectureSessionNotFound() {
        // Given
        LectureDto.LectureApplyRequest request = new LectureDto.LectureApplyRequest("U001", "S999");

        when(lectureRepository.findLectureSession(request.sessionId())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(LectureException.class, () -> lectureService.applyForLecture(request));
    }
}