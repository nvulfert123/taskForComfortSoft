package com.example.demo.util;

import com.example.demo.exceptions.InvalidDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class FileValidator {
    private static final String ALLOWED_DIR = "src/main/resources/tables";

    public void validatePath(Path path) {
        Path allowedPath = Paths.get(ALLOWED_DIR);
        if (!path.startsWith(allowedPath)) {
            throw new InvalidDataException("Access denied. Allowed dir: " + allowedPath);
        }
    }

    public void validateFile(File file) {
        if (!file.exists()) throw new InvalidDataException("File not found");
        if (!file.isFile()) throw new InvalidDataException("Path is not a file");
        if (!file.canRead()) throw new InvalidDataException("Can't read file");
    }
}