package edu.jsp.btm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.jsp.btm.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
