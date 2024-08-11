package com.org.mybookstore.api.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User registerUser(User user) {
		user.setPassword(user.getPassword());
		return userRepository.save(user);
	}

	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public Optional<User> loadUserByUsername(String username) {
		Optional<User> user = userRepository.findByUsername(username);

		return user;
	}
	
	public User updateUser(User user) {
		user.setPassword(user.getPassword());
		return userRepository.save(user);
	}
}
