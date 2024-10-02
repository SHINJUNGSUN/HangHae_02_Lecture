package io.hhplus.lecture.lecture.application;

import io.hhplus.lecture.lecture.domain.LectureApplicant;
import io.hhplus.lecture.lecture.domain.LectureSession;
import lombok.Builder;

import java.time.LocalDateTime;

public class LectureDto {
    public record LectureApplyRequest (
            String userId,
            String sessionId
    ) {
    }

    @Builder
    public record LectureApplyResponse (
        String lectureApplicantId,
        String lectureId,
        String sessionId,
        String title,
        String instructor,
        LocalDateTime sessionDatetime,
        String userId
    ) {
        public static LectureApplyResponse of(LectureSession session, LectureApplicant applicant) {
            return LectureApplyResponse.builder()
                    .lectureApplicantId(applicant.getLectureApplicantId())
                    .lectureId(session.getLectureId())
                    .sessionId(session.getSessionId())
                    .title(session.getLecture().getTitle())
                    .instructor(session.getLecture().getInstructor())
                    .sessionDatetime(session.getSessionDatetime())
                    .userId(applicant.getUserId())
                    .build();
        }
    }

    @Builder
    public record LectureSessionInfo (
            String lectureId,
            String sessionId,
            String title,
            String description,
            String instructor,
            LocalDateTime sessionDatetime,
            int maxCapacity,
            int applicantsCount
    ) {
        public static LectureSessionInfo from(LectureSession session) {
            return LectureSessionInfo.builder()
                    .lectureId(session.getLectureId())
                    .sessionId(session.getSessionId())
                    .title(session.getLecture().getTitle())
                    .description(session.getLecture().getDescription())
                    .instructor(session.getLecture().getInstructor())
                    .sessionDatetime(session.getSessionDatetime())
                    .maxCapacity(session.getMaxCapacity())
                    .applicantsCount(session.getApplicants().size())
                    .build();

        }
    }

    @Builder
    public record LectureApplicantInfo (
            String lectureApplicantId,
            String title,
            String description,
            String instructor,
            LocalDateTime sessionDatetime,
            LocalDateTime appliedAt
    ) {
        public static LectureApplicantInfo from(LectureApplicant applicant) {
            return LectureApplicantInfo.builder()
                    .lectureApplicantId(applicant.getLectureApplicantId())
                    .title(applicant.getLectureSession().getLecture().getTitle())
                    .description(applicant.getLectureSession().getLecture().getDescription())
                    .instructor(applicant.getLectureSession().getLecture().getInstructor())
                    .sessionDatetime(applicant.getLectureSession().getSessionDatetime())
                    .appliedAt(applicant.getAppliedAt())
                    .build();
        }
    }
}
