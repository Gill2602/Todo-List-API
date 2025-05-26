package com.gll.todoapi.responses;

public record TaskResponse(
        Long id,
        String title,
        String description
) {
}
