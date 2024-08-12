package com.todo.controller.impl;

import com.todo.controller.TodoController;
import com.todo.dto.TaskDTO;
import com.todo.model.Task;
import com.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TodoControllerImpl implements TodoController {

    private final TodoService todoService;

    @Override
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> taskDTO = todoService.getAllTasks();
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createNewTask(TaskDTO taskDTO) {
        Task task = todoService.createTask(taskDTO);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TaskDTO> updateTask(Long id, TaskDTO taskDTO) {
        TaskDTO task = todoService.updateTask(id, taskDTO);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteTask(Long id) {
        todoService.deleteTask(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TaskDTO> toggleTaskComplete(Long id) {
        TaskDTO taskDTO = todoService.toggleTaskComplete(id);
        return new ResponseEntity<>(taskDTO, HttpStatus.OK);
    }
}
