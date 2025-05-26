package com.gll.todoapi.controllers;

import com.gll.todoapi.requests.TaskRequest;
import com.gll.todoapi.responses.TaskResponse;
import com.gll.todoapi.services.TaskService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/todos")
public class TaskController {

    private final TaskService taskService;

    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody TaskRequest request) {
        return new ResponseEntity<>(taskService.create(request, userDetails), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@AuthenticationPrincipal UserDetails userDetails,
                                                   @RequestBody TaskRequest request,
                                                   @PathVariable("id") Long id) {
        return new ResponseEntity<>(taskService.update(request, id, userDetails), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable("id") Long id) {
        taskService.delete(id, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<Page<TaskResponse>> deleteTask(@AuthenticationPrincipal UserDetails userDetails,
                                                         Pageable pageable) {
        return new ResponseEntity<>(taskService.readAll(pageable, userDetails), HttpStatus.OK);
    }
}
