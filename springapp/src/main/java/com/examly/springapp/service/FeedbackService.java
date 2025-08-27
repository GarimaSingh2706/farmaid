package com.examly.springapp.service;

import java.util.List;
import com.examly.springapp.model.Feedback;

public interface FeedbackService {
    Feedback createFeedBack(Feedback feedback, long userId);

    Feedback getFeedbackById(long id);

    List<Feedback> getAllFeedbacks();

    List<Feedback> getFeedbacksByUserId(long userId);

    Feedback deleteFeedback(long id);

    List<Feedback> viewAllFeedback();
}