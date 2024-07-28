package com.example.demo.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ErrorDetail;
import com.example.demo.dto.Product;
import com.google.common.collect.Lists;

@RestController
public class ProductController {

    @GetMapping("/products/{name}")
    public ResponseEntity<Product> getProduct(@PathVariable String name) {
        Product product = new Product(name, 30);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@Validated @RequestBody Product product) {
        return ResponseEntity.created(URI.create("/products/" + product.getName())).body(product);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        ErrorDetail rootError = new ErrorDetail("1000000", "Validation failed");
        rootError.setTarget(ex.getObjectName());
        
        List<ErrorDetail> details = ex.getBindingResult().getFieldErrors()
            .stream()
            .map((fieldError) -> {
                String rawMessage = fieldError.getDefaultMessage();
                List<String> messageParts = Lists.newArrayList(rawMessage.split("\\|"));
                System.out.println(messageParts);

                String errorCode = Lists.newArrayList(fieldError.getCodes()).get(0);

                if (messageParts.size() > 1) {
                    String messageKey = messageParts.get(0);
                    errorCode = "[%s] %s".formatted(messageKey, errorCode);
                }

                List<String> messageTextArgs = null;
                if (messageParts.size() > 1) {
                    String messageTextArgsStr = messageParts.get(1);
                    messageTextArgs = Lists.newArrayList(messageTextArgsStr.split(","));
                }

                String errorMessage = "'%s' - %s".formatted(fieldError.getRejectedValue(), rawMessage);

                ErrorDetail detailError = new ErrorDetail(errorCode, errorMessage);
                detailError.setTarget(fieldError.getField());
                
                return detailError;
            })
            .collect(Collectors.toList());

            rootError.setDetails(details);

        Map<String, ErrorDetail> responseBody = new HashMap<>();
        responseBody.put("error", rootError);

        return ResponseEntity.ofNullable(responseBody);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<Object> handleJsonParseException(HttpMessageNotReadableException ex) {
        ErrorDetail rootError = new ErrorDetail("1000001", ex.getLocalizedMessage());

        Map<String, ErrorDetail> responseBody = new HashMap<>();
        responseBody.put("error", rootError);

        return ResponseEntity.ofNullable(responseBody);
    }
}
