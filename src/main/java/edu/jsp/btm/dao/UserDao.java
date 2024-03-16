package edu.jsp.btm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import edu.jsp.btm.entity.Role;
import edu.jsp.btm.entity.User;
import edu.jsp.btm.repository.UserRepository;

@Repository
public class UserDao {
	// save
	// findById
	// findByEmail
	// findAllEmployeee
	// delete

	private UserRepository userRepository;

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public Optional<User> findById(int userId) {
		return userRepository.findById(userId);
	}

	public Optional<User> findByEmail(String userEmail) {
		return userRepository.findByuserEmail(userEmail);
	}

	public List<User> findAllEmployee() {
		return userRepository.findAll(Role.EMPLOYEE);
	}

	public void delete(User user) {
		userRepository.delete(user);
	}

	public User findByEmailAndPassword(String userEmail, String userPassword) {
		return userRepository.findUserByUserEmailAndUserPassword(userEmail, userPassword);
	}

	public Optional<User> findUserByRole(Role manager) {
		return userRepository.findUserByRole(manager);
	}
}
