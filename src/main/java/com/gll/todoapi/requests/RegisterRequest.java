package com.gll.todoapi.requests;

public record RegisterRequest(
        String name,
        String email,
        String password
) {
}
