package io.hhplus.lecture.lecture.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "lecture_application")
public class LectureApplication {

    @Id
    @Column(name = "lecture_application_id")
    private String lectureApplicationId;

    @Column(name = "lecture_id", insertable = false, updatable = false)
    private String lectureId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "is_success")
    private boolean isSuccess;

    @Column(name ="registered_at")
    private LocalDateTime registeredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
}
