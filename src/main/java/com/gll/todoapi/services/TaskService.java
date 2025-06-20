package com.gll.todoapi.services;

import com.gll.todoapi.entities.TaskEntity;
import com.gll.todoapi.entities.UserEntity;
import com.gll.todoapi.entities.enums.TaskProgress;
import com.gll.todoapi.exceptions.NotFoundException;
import com.gll.todoapi.exceptions.UnauthorizedException;
import com.gll.todoapi.repositories.TaskRepository;
import com.gll.todoapi.repositories.UserRepository;
import com.gll.todoapi.requests.TaskRequest;
import com.gll.todoapi.responses.TaskResponse;
import com.gll.todoapi.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public TaskResponse create(TaskRequest request, UserDetails userDetails) {
        UserEntity user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("Not found user with email: " + userDetails.getUsername()));

        TaskEntity task = TaskEntity.builder()
                .title(request.title())
                .description(request.description())
                .progress(TaskProgress.TO_DO)
                .user(user)
                .build();

        Optional.ofNullable(request.expirationDate()).ifPresent(task::setExpirationDate);
        Optional.ofNullable(request.taskProgress()).ifPresent(task::setProgress);

        TaskEntity savedTask = taskRepository.save(task);

        return new TaskResponse(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getProgress(),
                savedTask.getExpirationDate()
        );
    }

    public TaskResponse update(TaskRequest request, Long id, UserDetails userDetails) {
        TaskEntity result = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found task with ID: " + id));

        if(!isOwner(result, userDetails)) {
            throw new UnauthorizedException("user is not the owner of the task");
        }

        if (AppUtils.isNotBlank(request.title())) {
            result.setTitle(request.title());
        }
        if (AppUtils.isNotBlank(request.description())) {
            result.setDescription(request.description());
        }

        Optional.ofNullable(request.expirationDate()).ifPresent(result::setExpirationDate);
        Optional.ofNullable(request.taskProgress()).ifPresent(result::setProgress);

        TaskEntity updatedTask = taskRepository.save(result);

        return new TaskResponse(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getProgress(),
                updatedTask.getExpirationDate()
        );
    }

    public void delete(Long id, UserDetails userDetails) {
        TaskEntity result = taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Not found task with ID: " + id));

        if(!isOwner(result, userDetails)) {
            throw new UnauthorizedException("user is not the owner of the task");
        }

        taskRepository.delete(result);
    }

    public Page<TaskResponse> readAll(Pageable pageable, UserDetails userDetails) {
        UserEntity user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("Not found user with email: " + userDetails.getUsername()));

        return taskRepository.findAllByUser(user, pageable)
                .map(taskEntity -> new TaskResponse(
                        taskEntity.getId(),
                        taskEntity.getTitle(),
                        taskEntity.getDescription(),
                        taskEntity.getProgress(),
                        taskEntity.getExpirationDate()
                ));
    }

    private boolean isOwner(TaskEntity result, UserDetails userDetails) {
        return result.getUser().getEmail().equals(userDetails.getUsername());
    }
}
