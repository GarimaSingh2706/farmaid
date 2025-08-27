package com.examly.springapp.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.examly.springapp.exception.FeedbackNotExist;
import com.examly.springapp.exception.UserNotFound;
import com.examly.springapp.model.Feedback;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.FeedbackRepo;
import com.examly.springapp.repository.UserRepo;
import com.examly.springapp.util.AppMessages;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	private final FeedbackRepo feedbackRepo;
	private final UserRepo userRepo;

	public FeedbackServiceImpl(FeedbackRepo feedbackRepo, UserRepo userRepo) {
		this.feedbackRepo = feedbackRepo;
		this.userRepo = userRepo;
	}

	public Feedback createFeedBack(Feedback feedback, long userId) {
		User user = userRepo.findById(userId).orElse(null);
		if (user == null) {
			throw new UserNotFound(AppMessages.USER_NOT_FOUND);
		}
		feedback.setUser(user);
		return feedbackRepo.save(feedback);
	}

	public Feedback getFeedbackById(long id) {
		Feedback feedback = feedbackRepo.findById(id).orElse(null);
		if (feedback != null) {
			return feedback;
		}
		throw new FeedbackNotExist(AppMessages.FEEDBACK_NOT_FOUND);
	}

	public List<Feedback> getAllFeedbacks() {
		return feedbackRepo.findAll();
	}

	public List<Feedback> getFeedbacksByUserId(long userId) {
		User user = userRepo.findById(userId).orElse(null);
		if (user != null) {
			return feedbackRepo.findByUserId(userId);
		}
		throw new UserNotFound(AppMessages.USER_NOT_FOUND);
	}

	public Feedback deleteFeedback(long id) {
		Feedback feedback = feedbackRepo.findById(id).orElse(null);
		if (feedbackRepo.existsById(id)) {
			feedbackRepo.deleteById(id);
			return feedback;
		}
		throw new FeedbackNotExist(AppMessages.FEEDBACK_NOT_FOUND);
	}

	@Override
	public List<Feedback> viewAllFeedback() {
		return feedbackRepo.findAll();
	}

}
