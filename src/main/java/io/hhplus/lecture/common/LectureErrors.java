package io.hhplus.lecture.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum LectureErrors {
    SESSION_NOT_FOUND(HttpStatus.NOT_FOUND, 100001,"존재하지 않는 특강 세션입니다."),
    SESSION_EXPIRED(HttpStatus.BAD_REQUEST, 100002,"이미 지난 특강 세션입니다."),
    SESSION_ALREADY_APPLIED(HttpStatus.BAD_REQUEST, 100003,"신청이 완료된 특강 세션입니다."),
    SESSION_CAPACITY_EXCEEDED(HttpStatus.BAD_REQUEST, 100004,"신청 가능 인원이 초과된 특강 세션입니다."),
    SESSION_APPLICANT_FAIL(HttpStatus.BAD_REQUEST, 100004,"특강 신청에 실패하였습니다.");

    HttpStatus status;
    int errorCode;
    String errorMessage;
}
