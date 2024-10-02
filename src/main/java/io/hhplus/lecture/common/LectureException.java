package io.hhplus.lecture.common;

import lombok.Getter;

@Getter
public class LectureException extends RuntimeException {

    private final LectureErrors error;

    public LectureException(LectureErrors error) {
        this.error = error;
    }
}
