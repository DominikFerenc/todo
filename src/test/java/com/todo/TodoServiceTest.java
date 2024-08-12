package com.todo;

import com.todo.dto.TaskDTO;
import com.todo.exception.ResourceNotFoundException;
import com.todo.model.Task;
import com.todo.repository.TaskRepository;
import com.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class TodoServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks_ShouldReturnListOfTaskDTO() {
        Task task = createTask(1L, "Title", "Content", false);
        TaskDTO taskDTO = createTaskDTO(1L, "Title", "Content", false);
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<TaskDTO> result = todoService.getAllTasks();

        assertNotNull(result, "Result should not be null");
        assertEquals(1, result.size(), "Result size should be 1");
        assertEquals(taskDTO, result.getFirst(), "TaskDTO should match expected");
    }

    @Test
    void createTask_ShouldReturnSavedTask() {
        TaskDTO taskDTO = createTaskDTO(null, "Title", "Content", false);
        Task taskToSave = createTask(null, "Title", "Content", false);
        Task savedTask = createTask(1L, "Title", "Content", false);

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = todoService.createTask(taskDTO);

        assertNotNull(result, "Result should not be null");
        assertEquals(savedTask.getId(), result.getId(), "Task ID should match");
        assertEquals(savedTask.getTitle(), result.getTitle(), "Task title should match");
        assertEquals(savedTask.getContent(), result.getContent(), "Task content should match");
        assertEquals(savedTask.isCompleted(), result.isCompleted(), "Task completion status should match");

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void updateTask_ShouldReturnUpdatedTaskDTO() {
        Long taskId = 1L;
        TaskDTO taskDTO = createTaskDTO(taskId, "Updated Title", "Updated Content", true);
        Task existingTask = createTask(taskId, "Old Title", "Old Content", false);
        Task updatedTask = createTask(taskId, "Updated Title", "Updated Content", true);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        TaskDTO result = todoService.updateTask(taskId, taskDTO);

        assertNotNull(result, "Result should not be null");
        assertEquals("Updated Title", result.getTitle(), "Task title should be updated");
        assertEquals("Updated Content", result.getContent(), "Task content should be updated");
        assertTrue(result.isCompleted(), "Task completion status should be updated");
    }

    @Test
    void updateTask_ShouldThrowResourceNotFoundException_WhenTaskNotFound() {
        Long taskId = 1L;
        TaskDTO taskDTO = createTaskDTO(taskId, "Title", "Content", false);
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> todoService.updateTask(taskId, taskDTO));
    }

    @Test
    void deleteTask_ShouldDeleteTask_WhenTaskExists() {
        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(true);

        todoService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }

    @Test
    void deleteTask_ShouldThrowResourceNotFoundException_WhenTaskNotFound() {
        Long taskId = 1L;
        when(taskRepository.existsById(taskId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> todoService.deleteTask(taskId));
    }

    @Test
    void toggleTaskComplete_ShouldReturnUpdatedTaskDTO() {
        Long taskId = 1L;
        Task task = createTask(taskId, "Title", "Content", false);
        Task updatedTask = createTask(taskId, "Title", "Content", true);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        TaskDTO result = todoService.toggleTaskComplete(taskId);

        assertNotNull(result, "Result should not be null");
        assertTrue(result.isCompleted(), "Task should be marked as completed");
    }

    @Test
    void toggleTaskComplete_ShouldThrowResourceNotFoundException_WhenTaskNotFound() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> todoService.toggleTaskComplete(taskId));
    }

    private TaskDTO createTaskDTO(Long id, String title, String content, boolean completed) {
        return TaskDTO.builder()
                .id(id)
                .title(title)
                .content(content)
                .completed(completed)
                .build();
    }

    private Task createTask(Long id, String title, String content, boolean completed) {
        return Task.builder()
                .id(id)
                .title(title)
                .content(content)
                .completed(completed)
                .build();
    }
}
