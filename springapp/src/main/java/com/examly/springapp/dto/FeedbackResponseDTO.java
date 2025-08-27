package com.examly.springapp.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponseDTO {
	private Long feedbackId;
	private String feedbackText;
	private LocalDate date;
	private Long userId;
	private String rating;
}

