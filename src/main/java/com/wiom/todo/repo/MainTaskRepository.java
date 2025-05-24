package com.wiom.todo.repo;

import com.wiom.todo.enums.TaskStatus;
import com.wiom.todo.model.MainTask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.net.ContentHandler;

public interface MainTaskRepository extends JpaRepository<MainTask,Long> {
    Page<MainTask> findByTaskStatus(TaskStatus taskStatus, Pageable pageable);
}
