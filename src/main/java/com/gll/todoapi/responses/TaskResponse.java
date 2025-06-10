package com.gll.todoapi.responses;

import com.gll.todoapi.entities.enums.TaskProgress;

import java.time.LocalDate;

public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskProgress progress,
        LocalDate expirationDate
) {
}
