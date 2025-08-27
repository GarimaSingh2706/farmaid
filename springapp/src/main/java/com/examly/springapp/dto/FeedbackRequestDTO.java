package com.examly.springapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequestDTO {
	@NotNull(message = "Kindly provide your auspicious feedback")
	private String feedbackText;
	private String rating;
}
