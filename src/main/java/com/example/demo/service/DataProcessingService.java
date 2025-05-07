package com.example.demo.service;


import com.example.demo.exceptions.InvalidDataException;
import com.example.demo.util.FileValidator;
import com.example.demo.util.NumbersValidator;
import com.example.demo.util.XlsxFileProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataProcessingService {

    private final FileValidator fileValidator;
    private final XlsxFileProcessor xlsxProcessor;
    private final NumbersValidator numbersValidator;

    public List<Integer> processInput(String filePath, int n) throws IOException, InvalidDataException {
        Path path = Paths.get(filePath).normalize();
        fileValidator.validatePath(path);

        File file = path.toFile();
        fileValidator.validateFile(file);

        List<Integer> numbers = xlsxProcessor.readNumbers(path);
        numbersValidator.validate(n, numbers);

        return numbers;
    }
}