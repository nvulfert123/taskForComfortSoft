package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Component
public class XlsxFileProcessor {
    public List<Integer> readNumbers(Path path) throws IOException {
        try (var inputStream = Files.newInputStream(path)) {
            return XlsxReader.readNumbersFromXlsx(inputStream);
        }
    }
}