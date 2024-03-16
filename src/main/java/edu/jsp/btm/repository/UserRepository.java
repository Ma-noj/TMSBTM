package edu.jsp.btm.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.jsp.btm.entity.Role;
import edu.jsp.btm.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByuserEmail(String userEmail);

	@Query("SELECT u FROM User u WHERE u.role=?1")
	List<User> findAll(Role employee);

	User findUserByUserEmailAndUserPassword(String userEmail, String userPassword);

	@Query("SELECT u FROM User u WHERE u.role = 1?")
	Optional<User> findUserByRole(Role manager);

}
