package com.wiom.todo.repo;

import com.wiom.todo.model.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubTaskRepository extends JpaRepository<SubTask,Long> {
}
