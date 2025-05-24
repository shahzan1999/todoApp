package com.wiom.todo;

import com.wiom.todo.dto.TaskDTO;
import com.wiom.todo.enums.Priority;
import com.wiom.todo.model.MainTask;
import com.wiom.todo.model.SubTask;
import com.wiom.todo.repo.MainTaskRepository;
import com.wiom.todo.repo.SubTaskRepository;
import com.wiom.todo.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TodoApplicationTests {

	@InjectMocks
	private TaskService taskService;

	@Mock
	private MainTaskRepository mainTaskRepository;

	@Mock
	private SubTaskRepository subTaskRepository;

	@Test
	void createSubTask_shouldSucceed_whenMainTaskExists() {
		Long mainTaskId = 1L;
		TaskDTO dto = new TaskDTO("Subtask", "Desc", LocalDate.now(), Priority.HIGH);
		MainTask mainTask = MainTask.builder().id(1).name("Main").build();

		when(mainTaskRepository.findById(mainTaskId)).thenReturn(Optional.of(mainTask));

		SubTask result = taskService.createSubTask(mainTaskId, dto);

		assertNotNull(result);
		assertEquals("Subtask", result.getName());
		verify(subTaskRepository).save(any(SubTask.class));
	}

	@Test
	void createSubTask_shouldFail_whenMainTaskNotFound() {
		when(mainTaskRepository.findById(anyLong())).thenReturn(Optional.empty());

		assertThrows(ResponseStatusException.class, () ->
				taskService.createSubTask(99L, new TaskDTO()));
	}

}
