//package com.wiom.todo.repo;
//
//import com.wiom.todo.model.MainTask;
//import com.wiom.todo.model.SubTask;
//import com.wiom.todo.model.Task;
//import org.springframework.stereotype.Repository;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Repository
//public class TaskRepository {
//
//    Map<String, Task> tasks;
//    public TaskRepository(){
//        tasks = new HashMap<>();
//    }
//
//    public Optional<MainTask> getMainTask(String id) {
//        Task task = tasks.get(id);
//        if (task instanceof MainTask) {
//            return Optional.of((MainTask) task);
//        }
//        return Optional.empty();
//    }
//
//    public Optional<SubTask> getSubTask(String id) {
//        Task task = tasks.get(id);
//        if (task instanceof SubTask) {
//            return Optional.of((SubTask) task);
//        }
//        return Optional.empty();
//    }
//
//    public Task save(Task task){
//         this.tasks.put(task.getId(), task);
//         return task;
//    }
//
//    public List<MainTask> getPaginatedMainTasks(int pageNumber, int pageSize) {
//        return tasks.values().stream()
//                .filter(task -> task instanceof MainTask)
//                .map(task -> (MainTask) task)
//                .skip((long) pageNumber * pageSize)
//                .limit(pageSize)
//                .collect(Collectors.toList());
//    }
//
//
//}
