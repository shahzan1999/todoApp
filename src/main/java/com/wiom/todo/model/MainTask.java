package com.wiom.todo.model;

import com.wiom.todo.dto.TaskDTO;
import com.wiom.todo.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "main_tasks")
public class MainTask extends Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToMany(mappedBy = "mainTask", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<SubTask> subTaskList;

    public static MainTask createNew() {
        MainTask mainTask = new MainTask();
        mainTask.setTaskStatus(TaskStatus.PENDING);
        mainTask.setCreatedAt(LocalDateTime.now());
        mainTask.setUpdatedAt(LocalDateTime.now());
        mainTask.setSubTaskList(new ArrayList<>());
        return mainTask;
    }

    public void update(TaskDTO taskDTO) {
        this.setName(taskDTO.getName());
        this.setDescription(taskDTO.getTaskDescription());
        this.setDueDate(taskDTO.getDueDate());
        this.setPriority(taskDTO.getPriority());
    }

    public double getCompletionPercentage() {
        if (CollectionUtils.isEmpty(this.subTaskList)) {
            return 0;
        }
        int completedTask = (int) this.subTaskList.stream()
                .filter(subTask -> subTask.getTaskStatus().equals(TaskStatus.COMPLETED))
                .count();
        return ((double) completedTask / this.subTaskList.size()) * 100;
    }
}
