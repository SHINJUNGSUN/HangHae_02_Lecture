package io.hhplus.lecture.lecture.presentation;

import io.hhplus.lecture.lecture.application.LectureDto;
import io.hhplus.lecture.lecture.application.LectureService;
import io.hhplus.lecture.lecture.domain.LectureSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    /**
     * 특강을 신청한다.
     */
    @PostMapping("apply")
    public ResponseEntity<LectureDto.LectureApplyResponse> applyForLecture(@RequestBody LectureDto.LectureApplyRequest request) {

        return ResponseEntity.ok(lectureService.applyForLecture(request));
    }
}
