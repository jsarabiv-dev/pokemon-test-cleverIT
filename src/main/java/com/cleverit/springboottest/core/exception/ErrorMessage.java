package com.cleverit.springboottest.core.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorMessage {

    private int statusCode;
    private LocalDateTime timestamp;
    private String message;
    private String description;
    private String debugMessage;
}
