package com.gll.todoapi.requests;

import com.gll.todoapi.entities.enums.TaskProgress;

import java.time.LocalDate;

public record TaskRequest(
        String title,
        String description,
        LocalDate expirationDate,
        TaskProgress taskProgress
) {
}
