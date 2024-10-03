package io.hhplus.lecture;

import io.hhplus.lecture.lecture.application.LectureDto;
import io.hhplus.lecture.lecture.application.LectureService;
import io.hhplus.lecture.lecture.domain.LectureApplicant;
import io.hhplus.lecture.lecture.domain.LectureRepository;
import io.hhplus.lecture.lecture.domain.LectureSession;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class LectureApplicationTests {

	@Autowired
	LectureService lectureService;

	@Autowired
	LectureRepository lectureRepository;

	@Test
	@DisplayName("특강 신청 통합 테스트: 동일한 특강에 대해 40명이 신청했을 때, 30명만 성공")
	void applyForLecture_maxCapacity() throws InterruptedException {

		// Given
		String sessionId = "S001";

		int maxCapacity = 30;
		int applicantsCount = 40;

		ExecutorService executorService = Executors.newFixedThreadPool(applicantsCount);
		CountDownLatch latch = new CountDownLatch(applicantsCount);

		// When
		for(int i = 0; i < applicantsCount; i++) {
			LectureDto.LectureApplyRequest request = new LectureDto.LectureApplyRequest("U00" + (i + 1), sessionId);
			executorService.submit(() -> {
				try {
					lectureService.applyForLecture(request);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executorService.shutdown();

		// Then
		LectureSession session = lectureRepository.findLectureSession(sessionId).orElseThrow();
		List<LectureApplicant> lectureApplicants = lectureRepository.findLectureApplicantListBySessionId(sessionId);

		assertEquals(lectureApplicants.size(), session.getApplicantsCount());
		assertEquals(maxCapacity, lectureApplicants.size());
	}

	@Test
	@DisplayName("동일한 유저 정보로 같은 특강을 5번 신청했을 때, 1번만 성공")
	void applyForLecture_duplicateApplication() throws InterruptedException {

		// Given
		String sessionId = "S001";
		String userId = "U001";

		int tryCount = 5;

		ExecutorService executorService = Executors.newFixedThreadPool(tryCount);
		CountDownLatch latch = new CountDownLatch(tryCount);

		// When
		for(int i = 0; i < tryCount; i++) {
			LectureDto.LectureApplyRequest request = new LectureDto.LectureApplyRequest(userId, sessionId);
			executorService.submit(() -> {
				try {
					lectureService.applyForLecture(request);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		executorService.shutdown();

		// Then
		LectureSession session = lectureRepository.findLectureSession(sessionId).orElseThrow();

		assertEquals(1, session.getApplicantsCount());
	}
}
