package com.haruse3kki.haruse3kki.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final int status;

    public CustomException(int status, String message) {
        super(message);
        this.status = status;
    }
}
