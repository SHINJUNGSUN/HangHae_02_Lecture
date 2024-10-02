package io.hhplus.lecture.lecture.presentation;

import io.hhplus.lecture.lecture.application.LectureDto;
import io.hhplus.lecture.lecture.application.LectureService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @PostMapping("/apply")
    public ResponseEntity<LectureDto.LectureApplyResponse> applyForLecture(@RequestBody LectureDto.LectureApplyRequest request) {
        return ResponseEntity.ok(lectureService.applyForLecture(request));
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<LectureDto.LectureSessionInfo>> searchLectureSessions(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok(lectureService.searchLectureSessions(date));
    }

    @GetMapping("/applicants/{userId}")
    public ResponseEntity<List<LectureDto.LectureApplicantInfo>> searchLectureApplicants(@PathVariable String userId) {
        return ResponseEntity.ok(lectureService.searchLectureApplicants(userId));
    }
}
