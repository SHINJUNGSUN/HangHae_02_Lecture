package io.hhplus.lecture.common;

public record ErrorResponse(
        int errorCode,
        String errorMessage
) {
}
