package edu.jsp.btm.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.jsp.btm.entity.Task;
import edu.jsp.btm.repository.TaskRepository;

@Repository
public class TaskDao {

	// save
	// findById
	// findAll
	// delete
	@Autowired
	private TaskRepository taskRepository;

	public Task saveTask(Task task) {
		return taskRepository.save(task);
	}

	public Optional<Task> findById(int taskId) {
		return taskRepository.findById(taskId);
	}

	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	public void deleteTask(Task task) {
		taskRepository.delete(task);
	}

}
