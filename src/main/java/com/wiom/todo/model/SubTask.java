package com.wiom.todo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wiom.todo.dto.TaskDTO;
import com.wiom.todo.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "sub_tasks")
public class SubTask extends Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "main_task_id")
    @JsonIgnore
    private MainTask mainTask;

    public static SubTask from(TaskDTO taskDTO) {
        return SubTask.builder()
                .name(taskDTO.getName())
                .description(taskDTO.getTaskDescription())
                .taskStatus(TaskStatus.PENDING)
                .priority(taskDTO.getPriority())
                .dueDate(taskDTO.getDueDate())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "id=" + id +
                '}';
    }
}
