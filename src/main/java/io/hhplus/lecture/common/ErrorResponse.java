package io.hhplus.lecture.common;

import org.springframework.http.HttpStatus;

public record ErrorResponse(
        int errorCode,
        String errorMessage
) {
}
