package com.examly.springapp.service;

import java.util.List;
import com.examly.springapp.model.User;

public interface UserService {
	User createUser(User user);

	User loginUser(String username);

	List<User> getAllUsers();

	User getUserById(long userId);

	boolean deleteUser(long userId);
}
