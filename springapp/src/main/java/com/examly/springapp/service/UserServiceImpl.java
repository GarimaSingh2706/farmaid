package com.examly.springapp.service;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepo;
@Service
public class UserServiceImpl implements UserService {
	private final UserRepo userRepo;
	private final PasswordEncoder encoder;

	public UserServiceImpl(UserRepo userRepo, PasswordEncoder encoder) {
		this.userRepo = userRepo;
		this.encoder = encoder;
	}

	@Override
	public User createUser(User user) {
		if (userRepo.findByEmail(user.getEmail()) != null) {
			return null;
		}
		user.setPassword(encoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public User loginUser(String username) {
		return userRepo.findByEmail(username);
	}

	@Override
	public User getUserById(long userId) {
		return userRepo.findById(userId).orElse(null);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public boolean deleteUser(long userId) {
		if(userRepo.existsById(userId)) {
			userRepo.deleteById(userId);
			return true;
		}
		return false;
	}
}
