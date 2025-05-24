package com.wiom.todo.controller;

import com.wiom.todo.dto.TaskDTO;
import com.wiom.todo.enums.TaskStatus;
import com.wiom.todo.model.MainTask;
import com.wiom.todo.model.SubTask;
import com.wiom.todo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class MainTaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<MainTask> getAllTasks(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(required = false) TaskStatus status) {
        return taskService.getPaginatedMainTasks(pageNumber, pageSize,status);
    }
    @PostMapping
    public MainTask createTask(@RequestBody TaskDTO taskDTO){
        return taskService.createMainTask(taskDTO);
    }

    @PutMapping("/{taskId}")
    public MainTask updateTask(@PathVariable Long taskId,@RequestBody TaskDTO taskDTO){
        return taskService.updateMainTask(taskId,taskDTO);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long taskId){
        taskService.deleteMainTask(taskId);
        return ResponseEntity.ok("Task deleted successfully.");
    }

    @PostMapping("/{taskId}/subtask")
    public SubTask createTask(@PathVariable Long taskId, @RequestBody TaskDTO taskDTO){
        return taskService.createSubTask(taskId,taskDTO);
    }

    @PatchMapping("/subtask/{subTaskId}/complete")
    public SubTask completeSubTask(@PathVariable Long subTaskId) {
        return taskService.setSubtaskCompleted(subTaskId);
    }

    @PatchMapping("/{taskId}/complete")
    public MainTask completeMainTask(@PathVariable Long taskId) {
        return taskService.setMainTaskCompleted(taskId);
    }

}
