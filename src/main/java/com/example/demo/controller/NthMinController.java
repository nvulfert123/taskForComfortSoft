package com.example.demo.controller;

import com.example.demo.service.NthMinFinder;
import com.example.demo.util.FileValidator;
import com.example.demo.util.NumbersValidator;
import com.example.demo.util.XlsxFileProcessor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Поиск N-ого минимума", description = "API для поиска N-го минимального значения в *.xlsx файле")
public class NthMinController {

    private final FileValidator fileValidator;
    private final XlsxFileProcessor xlsxProcessor;
    private final NumbersValidator numbersValidator;

    @Operation(
            summary = "Найти N-й минимум",
            description = "Возвращает N-е минимальное значение из чисел в первом столбце Excel-файла"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешный результат",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Integer.class),
                            examples = @ExampleObject(value = "5")
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные входные параметры",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            examples = @ExampleObject(value = "File not found")
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Внутренняя ошибка сервера",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            examples = @ExampleObject(value = "File processing error")
                    )
            )
    })

    @PostMapping("/findNthMin")
    public ResponseEntity<?> findNthMin(
            @Parameter(
                    description = "Путь к Excel-файлу",
                    required = true,
                    example = "src/main/resources/tables/table.xlsx"
            )
            @RequestParam("filePath") String filePath,
            @Parameter(
                    description = "Порядковый номер минимума (N > 0)",
                    required = true,
                    example = "25"
            )
            @RequestParam("N") int n) throws IOException {

        log.info("Processing request for file: {}, N: {}", filePath, n);

        Path path = Paths.get(filePath).normalize();

        fileValidator.validatePath(path);
        File file = path.toFile();
        fileValidator.validateFile(file);

        List<Integer> numbers = xlsxProcessor.readNumbers(path);
        numbersValidator.validate(n, numbers);

        return ResponseEntity.ok(NthMinFinder.findNthMinimum(numbers, n));
    }
}