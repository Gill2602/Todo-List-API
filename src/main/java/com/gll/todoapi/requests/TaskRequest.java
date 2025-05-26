package com.gll.todoapi.requests;

public record TaskRequest(
        String title,
        String description
) {
}
