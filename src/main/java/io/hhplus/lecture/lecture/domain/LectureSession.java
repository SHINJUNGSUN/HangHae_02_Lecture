package io.hhplus.lecture.lecture.domain;

import io.hhplus.lecture.common.LectureErrors;
import io.hhplus.lecture.common.LectureException;
import io.hhplus.lecture.lecture.application.LectureService;
import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lecture_session")
public class LectureSession {

    private static final Logger log = LoggerFactory.getLogger(LectureService.class);

    @Id
    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "lecture_id", insertable = false, updatable = false)
    private String lectureId;

    @Column(name = "session_datetime")
    private LocalDateTime sessionDatetime;

    @Column(name = "max_capacity")
    private int maxCapacity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Builder.Default
    @OneToMany(mappedBy = "lectureSession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LectureApplicant> applicants = new ArrayList<>();

    public LectureApplicant applyForLecture(String userId) {

        if(sessionDatetime.isBefore(LocalDateTime.now())) {
            log.error(LectureErrors.SESSION_EXPIRED.getErrorMessage());
            throw new LectureException(LectureErrors.SESSION_EXPIRED);
        }

        if(this.applicants.stream().anyMatch(applicant -> applicant.getUserId().equals(userId))) {
            log.error(LectureErrors.SESSION_ALREADY_APPLIED.getErrorMessage());
            throw new LectureException(LectureErrors.SESSION_ALREADY_APPLIED);
        }

        if(this.applicants.size() >= this.maxCapacity) {
            log.error(LectureErrors.SESSION_CAPACITY_EXCEEDED.getErrorMessage());
            throw new LectureException(LectureErrors.SESSION_CAPACITY_EXCEEDED);
        }

        return LectureApplicant.builder()
                .sessionId(this.sessionId)
                .userId(userId)
                .appliedAt(LocalDateTime.now())
                .build();
    }
}
