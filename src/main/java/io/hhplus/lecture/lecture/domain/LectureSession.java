package io.hhplus.lecture.lecture.domain;

import io.hhplus.lecture.common.LectureErrors;
import io.hhplus.lecture.common.LectureException;
import jakarta.persistence.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lecture_session")
public class LectureSession {

    private static final Logger log = LoggerFactory.getLogger(LectureSession.class);

    @Id
    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "lecture_id", insertable = false, updatable = false)
    private String lectureId;

    @Column(name = "session_datetime")
    private LocalDateTime sessionDatetime;

    @Column(name = "max_capacity")
    private int maxCapacity;

    @Column(name = "applicants_count")
    private int applicantsCount;

//    @Version
//    private long version;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    public LectureApplicant applyForLecture(String userId) {

        if(sessionDatetime.isBefore(LocalDateTime.now())) {
            log.error(LectureErrors.SESSION_EXPIRED.getErrorMessage());
            throw new LectureException(LectureErrors.SESSION_EXPIRED);
        }

        if(this.applicantsCount >= this.maxCapacity) {
            log.error(LectureErrors.SESSION_CAPACITY_EXCEEDED.getErrorMessage());
            throw new LectureException(LectureErrors.SESSION_CAPACITY_EXCEEDED);
        }

        this.applicantsCount++;

        return LectureApplicant.builder()
                .sessionId(this.sessionId)
                .userId(userId)
                .appliedAt(LocalDateTime.now())
                .build();
    }
}
