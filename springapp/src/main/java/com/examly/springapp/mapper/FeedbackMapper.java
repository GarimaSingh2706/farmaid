package com.examly.springapp.mapper;

import java.time.LocalDate;
import com.examly.springapp.dto.FeedbackRequestDTO;
import com.examly.springapp.dto.FeedbackResponseDTO;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.User;

public class FeedbackMapper {
	public static Feedback toEntity(FeedbackRequestDTO dto, User user ) { 
		Feedback feedback = new Feedback();
		feedback.setFeedbackText(dto.getFeedbackText());
		feedback.setRating(dto.getRating());
		feedback.setDate(LocalDate.now());
		feedback.setUser(user);
		return feedback;
	}

	public static FeedbackResponseDTO toDTO(Feedback feedback) {
		FeedbackResponseDTO dto = new FeedbackResponseDTO();
		dto.setFeedbackId(feedback.getFeedbackId());
		dto.setFeedbackText(feedback.getFeedbackText());
		dto.setDate(feedback.getDate());
		dto.setUserId(feedback.getUser().getUserId());
		dto.setRating(feedback.getRating());
		return dto;
	}
}
