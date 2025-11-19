package com.example.loomi.api.Handlers;

public record ErrorResponse(
    int status,
    String message,
    String details
) {
    
}
