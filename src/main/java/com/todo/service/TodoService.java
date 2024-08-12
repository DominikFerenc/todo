package com.todo.service;


import com.todo.dto.TaskDTO;
import com.todo.exception.ResourceNotFoundException;
import com.todo.model.Task;
import com.todo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    public final TaskRepository taskRepository;

    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    public Task createTask(TaskDTO taskDTO) {
        Task task = convertToEntity(taskDTO);
        return saveTask(task);
    }

    private Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setTitle(taskDTO.getTitle());
            task.setContent(taskDTO.getContent());
            task.setCompleted(taskDTO.isCompleted());
            Task updateTask = saveTask(task);
            return convertToDto(updateTask);
        } else {
            throw new ResourceNotFoundException("Task not found with id " + id);
        }
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id " + id);
        }
        taskRepository.deleteById(id);
    }

    public TaskDTO toggleTaskComplete(Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setCompleted(!task.isCompleted());
            Task updateTask = saveTask(task);
            return convertToDto(updateTask);
        } else {
            throw new ResourceNotFoundException("Task not found with id " + id);
        }
    }


    public TaskDTO convertToDto(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .content(task.getContent())
                .completed(task.isCompleted())
                .build();
    }

    public Task convertToEntity(TaskDTO taskDTO) {
        return Task.builder()
                .id(taskDTO.getId())
                .title(taskDTO.getTitle())
                .content(taskDTO.getContent())
                .completed(taskDTO.isCompleted())
                .build();
    }
}
