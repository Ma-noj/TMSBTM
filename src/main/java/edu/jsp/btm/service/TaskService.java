package edu.jsp.btm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.jsp.btm.dao.TaskDao;
import edu.jsp.btm.dao.UserDao;
import edu.jsp.btm.entity.Role;
import edu.jsp.btm.entity.Task;
import edu.jsp.btm.entity.User;
import edu.jsp.btm.exception.TaskNotAssigiendException;
import edu.jsp.btm.exception.UserNotFoundException;

@Service
public class TaskService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private TaskDao taskDao;

	public void saveTask(int userId, Task task) {
		Optional<User> optional = userDao.findById(userId);
		if (optional.isPresent()) {
			User user = optional.get();
			if (user.getRole() == Role.EMPLOYEE) {
				if (user.getTasks() == null) {
					List<Task> tasks = new ArrayList<>();
					user.setTasks(tasks);
				}
				task.setUser(user);
				Task savedTask = taskDao.saveTask(task);
//				user.getTasks().add(savedTask);
//				userDao.saveUser(user);

				List<Task> listOfTask = user.getTasks();
				listOfTask.add(savedTask);
				user.setTasks(listOfTask);
				userDao.saveUser(user);

			}
			throw new TaskNotAssigiendException("User Not Have a Role Employee");

		}
		throw new UserNotFoundException("User With the Given UserId " + userId + " Not Found");
	}
}
