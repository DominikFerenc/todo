package com.todo.controller;


import com.todo.dto.TaskDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/api/tasks")
public interface TodoController {
    @GetMapping()
    public ResponseEntity<List<TaskDTO>> getAllTasks();

    @PostMapping()
    public ResponseEntity<?> createNewTask(@RequestBody TaskDTO taskDTO);

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO);

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id);

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<TaskDTO> toggleTaskComplete(@PathVariable Long id);

}
