package com.wiom.todo.dto;

import com.wiom.todo.enums.Priority;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private String name;
    private String taskDescription;
    private LocalDate dueDate;
    private Priority priority;

}
