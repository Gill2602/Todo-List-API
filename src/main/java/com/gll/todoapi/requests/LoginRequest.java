package com.gll.todoapi.requests;

public record LoginRequest(
        String email,
        String password
) {
}
