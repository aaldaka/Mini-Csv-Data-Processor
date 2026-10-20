package com.ga.mini_concurrency.dataprocessor.Exception;

public class FileNotFound extends RuntimeException {
    public FileNotFound(String message) {
        super(message);
    }
}
