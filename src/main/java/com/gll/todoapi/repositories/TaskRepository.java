package com.gll.todoapi.repositories;

import com.gll.todoapi.entities.TaskEntity;
import com.gll.todoapi.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity, Long>{

    Page<TaskEntity> findAllByUser(UserEntity user, Pageable pageable);
}
