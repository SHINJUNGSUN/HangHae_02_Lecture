package io.hhplus.lecture.lecture.application;

import io.hhplus.lecture.lecture.domain.LectureApplicant;
import io.hhplus.lecture.lecture.domain.LectureSession;
import lombok.Builder;

import java.time.LocalDateTime;

public class LectureDto {
    public record LectureApplyRequest(
            String userId,
            String sessionId
    ) {

    }

    @Builder
    public record LectureApplyResponse (
        String lectureApplicantId,
        String title,
        String instructor,
        LocalDateTime sessionDatetime,
        String userId
    ) {
        public static LectureApplyResponse of(LectureSession session, LectureApplicant applicant) {
            return LectureApplyResponse.builder()
                    .lectureApplicantId(applicant.getLectureApplicantId())
                    .title(session.getLecture().getTitle())
                    .instructor(session.getLecture().getInstructor())
                    .sessionDatetime(session.getSessionDatetime())
                    .userId(applicant.getUserId())
                    .build();
        }
    }
}
