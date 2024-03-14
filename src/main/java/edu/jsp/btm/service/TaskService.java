package edu.jsp.btm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.jsp.btm.dao.TaskDao;
import edu.jsp.btm.dao.UserDao;
import edu.jsp.btm.entity.Role;
import edu.jsp.btm.entity.Task;
import edu.jsp.btm.entity.User;
import edu.jsp.btm.exception.TaskNotAssigiendException;
import edu.jsp.btm.exception.TaskNotFoundException;
import edu.jsp.btm.exception.UserNotFoundException;
import edu.jsp.btm.util.ResponseStructure;

@Service
public class TaskService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private TaskDao taskDao;

	public ResponseEntity<ResponseStructure<Task>> saveTask(int userId, Task task) {
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

				ResponseStructure<Task> responseStructure = new ResponseStructure<>();
				responseStructure.setStatusCode(HttpStatus.CREATED.value());
				responseStructure.setMessage("Created");
				responseStructure.setData(savedTask);
				return new ResponseEntity<ResponseStructure<Task>>(responseStructure, HttpStatus.CREATED);

			}
			throw new TaskNotAssigiendException("User Not Have a Role Employee");

		}
		throw new UserNotFoundException("User With the Given UserId " + userId + " Not Found");
	}

	public ResponseEntity<ResponseStructure<Task>> updatetask(int taskId, Task task) {
		Optional<Task> optional = taskDao.findById(taskId);
		if (optional.isPresent()) {
			task.setTaskId(taskId);
			ResponseStructure<Task> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Modified");
			responseStructure.setData(taskDao.saveTask(task));
			return new ResponseEntity<ResponseStructure<Task>>(responseStructure, HttpStatus.OK);
		}
		throw new TaskNotFoundException("Task With the Given Id" + taskId + " Not Found");
	}

	public ResponseEntity<ResponseStructure<Task>> findTaskById(int taskId) {
		Optional<Task> optional = taskDao.findById(taskId);
		if (optional.isPresent()) {
			ResponseStructure<Task> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Found");
			responseStructure.setData(optional.get());
			return new ResponseEntity<ResponseStructure<Task>>(responseStructure, HttpStatus.OK);
		}
		throw new TaskNotFoundException("Task With the Given Id" + taskId + " Not Found");
	}

	public ResponseEntity<ResponseStructure<List<Task>>> findAll() {
		ResponseStructure<List<Task>> responseStructure = new ResponseStructure<>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Found");
		responseStructure.setData(taskDao.findAll());
		return new ResponseEntity<ResponseStructure<List<Task>>>(responseStructure, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<List<Task>>> findAllByEmployeeId(int employeeId) {
		Optional<User> optional = userDao.findById(employeeId);
		if (optional.isPresent()) {
			ResponseStructure<List<Task>> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Found");
			responseStructure.setData(optional.get().getTasks());
			return new ResponseEntity<ResponseStructure<List<Task>>>(responseStructure, HttpStatus.OK);
		}
		throw new UserNotFoundException("User withe the given Id = " + employeeId + " Not Found");
	}

	public ResponseEntity<ResponseStructure<String>> deleteTaskById(int taskId) {
		Optional<Task> optional = taskDao.findById(taskId);
		if (optional.isPresent()) {
			taskDao.deleteTask(optional.get());
			ResponseStructure<String> responseStructure = new ResponseStructure<>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage("Removed");
			responseStructure.setData("Removed");
			return new ResponseEntity<ResponseStructure<String>>(responseStructure, HttpStatus.OK);
		}
		throw new TaskNotFoundException("Task With the Given Id" + taskId + " Not Found");
	}
}
