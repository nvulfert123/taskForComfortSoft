package com.example.demo.util;

import com.example.demo.exceptions.InvalidDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class NumbersValidator {
    public void validate(int n, List<Integer> numbers) {
        if (n <= 0) throw new InvalidDataException("N must be > 0");
        if (numbers.isEmpty()) throw new InvalidDataException("No valid numbers found");
        if (n > numbers.size()) throw new InvalidDataException("N exceeds the number of records in the file");
    }
}