package com.wiom.todo.service;

import com.wiom.todo.dto.TaskDTO;
import com.wiom.todo.enums.TaskStatus;
import com.wiom.todo.model.MainTask;
import com.wiom.todo.model.SubTask;
import com.wiom.todo.repo.MainTaskRepository;
import com.wiom.todo.repo.SubTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private MainTaskRepository mainTaskRepository;
    @Autowired
    private SubTaskRepository subTaskRepository;

    public MainTask createMainTask(TaskDTO taskDTO){
        // todo fetch user id from securitycontext principle and save it in created by/updated by
        MainTask task = MainTask.createNew();
        task.update(taskDTO);
        return mainTaskRepository.save(task);
    }

    public SubTask createSubTask(Long mainTaskId,TaskDTO taskDTO){
        MainTask mainTask = mainTaskRepository.findById(mainTaskId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Main Task Id!"));
        SubTask subTask = SubTask.from(taskDTO);
        subTask.setMainTask(mainTask);
        subTaskRepository.save(subTask);
        return subTask;
    }

    public List<MainTask> getPaginatedMainTasks(int pageNumber, int pageSize, TaskStatus status) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "id"));
        if (status != null) {
            return mainTaskRepository.findByTaskStatus(status, pageable).getContent();
        } else {
            return mainTaskRepository.findAll(pageable).getContent();
        }
    }

    public SubTask setSubtaskCompleted(Long id){
        // todo need to check due date ??
        SubTask subTask = subTaskRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid  Task Id!"));
        subTask.setTaskStatus(TaskStatus.COMPLETED);
        subTaskRepository.save(subTask);
        MainTask mainTask = subTask.getMainTask();
        if(mainTask.getCompletionPercentage()==100){
            mainTask.setTaskStatus(TaskStatus.COMPLETED);
            mainTaskRepository.save(mainTask);
        }
        return subTask;
    }

    public MainTask setMainTaskCompleted(Long id){
        MainTask mainTask = mainTaskRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid  Task Id!"));
        if(mainTask.getCompletionPercentage()<100){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid Request!");
        }
        mainTask.setTaskStatus(TaskStatus.COMPLETED);
        return mainTaskRepository.save(mainTask);
    }

    public MainTask updateMainTask(Long taskId, TaskDTO taskDTO) {
        MainTask mainTask = mainTaskRepository.findById(taskId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid  Task Id!"));
        mainTask.update(taskDTO);
        return mainTaskRepository.save(mainTask);
    }

    public void deleteMainTask(Long taskId) {
        MainTask mainTask = mainTaskRepository.findById(taskId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid  Task Id!"));
        subTaskRepository.deleteAll(mainTask.getSubTaskList());
        mainTaskRepository.delete(mainTask);
    }

}
