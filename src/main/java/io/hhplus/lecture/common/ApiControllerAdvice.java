package io.hhplus.lecture.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = LectureException.class)
    public ResponseEntity<ErrorResponse> handleLectureException(LectureException e) {
        return ResponseEntity.status(e.getError().getStatus())
                .body(new ErrorResponse(e.getError().getErrorCode(), e.getError().getErrorMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(999999, "서비스 에러가 발생했습니다."));
    }
}
