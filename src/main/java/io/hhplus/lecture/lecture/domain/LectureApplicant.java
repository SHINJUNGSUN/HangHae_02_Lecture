package io.hhplus.lecture.lecture.domain;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lecture_applicant")
public class LectureApplicant {

    @Id
    @Column(name = "lecture_applicant_id")
    private String lectureApplicantId;

    @Column(name = "session_id")
    private String sessionId;

    @Column(name = "user_id")
    private String userId;

    @Column(name ="applied_at")
    private LocalDateTime appliedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", insertable = false, updatable = false)
    private LectureSession lectureSession;

    @PrePersist
    private void onCreate() {
        this.lectureApplicantId = "LA" + TSID.fast();
    }
}
