package com.examly.springapp.controller;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.examly.springapp.dto.FeedbackRequestDTO;
import com.examly.springapp.dto.FeedbackResponseDTO;
import com.examly.springapp.mapper.FeedbackMapper;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.User;
import com.examly.springapp.service.FeedbackService;
import com.examly.springapp.service.UserService;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
	private final FeedbackService feedbackService;
	private final UserService userService;
	private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

	public FeedbackController(FeedbackService feedbackService, UserService userService) {
		this.feedbackService = feedbackService;
		this.userService = userService;
	}

	@PostMapping("/user/{userId}")
	public ResponseEntity<?> createFeedBack(@Valid @RequestBody FeedbackRequestDTO feedbackRequestDTO,
			@PathVariable long userId) {
		logger.info("Feedback creation requested by user {}", userId);
		User user = userService.getUserById(userId);
		Feedback feedback = FeedbackMapper.toEntity(feedbackRequestDTO, user);
		feedback = feedbackService.createFeedBack(feedback, userId);
		return ResponseEntity.status(HttpStatus.CREATED).body(feedbackRequestDTO);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> viewFeedbackById(@PathVariable long id) {
		logger.info("Fetching feedback with ID {}", id);
		Feedback feedback = feedbackService.getFeedbackById(id);
		FeedbackResponseDTO dto = FeedbackMapper.toDTO(feedback);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	@GetMapping
	public ResponseEntity<?> getAllFeedbacks() {
		logger.info("Fetching all feedbacks");
		List<Feedback> feedbackList = feedbackService.getAllFeedbacks();
		List<FeedbackResponseDTO> dtos = feedbackList.stream()
				.map(FeedbackMapper::toDTO)
				.collect(Collectors.toList());
		logger.info("Total feedbacks fetched: {}", dtos.size());
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getFeedbacksByUserId(@PathVariable long userId) {
		logger.info("Fetching feedbacks for user ID: {}", userId);
		List<Feedback> feedbacks = feedbackService.getFeedbacksByUserId(userId);
		List<FeedbackResponseDTO> dtos = feedbacks.stream()
				.map(FeedbackMapper::toDTO).collect(Collectors.toList());
		logger.info("Fetched {} feedback(s) for user ID: {}", dtos.size(), userId);
		return ResponseEntity.status(HttpStatus.OK).body(dtos);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteFeedback(@PathVariable long id) {
		logger.info("Delete feedback with ID: {}", id);
		Feedback deleted = feedbackService.deleteFeedback(id);
		FeedbackResponseDTO dto = FeedbackMapper.toDTO(deleted);
		logger.info("Feedback deleted with ID: {}", id);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}
}