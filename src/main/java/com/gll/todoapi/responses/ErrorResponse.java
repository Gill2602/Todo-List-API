package com.gll.todoapi.responses;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        int status,
        LocalDateTime zuluTime
) {
}
