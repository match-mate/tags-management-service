package com.leaseforlove.tagsmanagementservice.application.http.configs.handlers;

import com.leaseforlove.tagsmanagementservice.application.web.dto.ErrorMessageDTO;
import com.leaseforlove.tagsmanagementservice.domain.exceptions.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class DomainExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DomainExceptionHandler.class);
    private static final Map<String, HttpStatus> statusTable = new HashMap<>();

    static {
        statusTable.put("DomainException", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorMessageDTO> handleDomainException(DomainException ex, HttpServletRequest request) {
        logger.error("Exception handled: {}", ex.getMessage(), ex);

        HttpStatus status = mapStatus(ex);

        ErrorMessageDTO errorResponse = ErrorMessageDTO
                .builder()
                .type(ex.getType())
                .message(ex.getMessage())
                .uri(request.getRequestURI())
                .details(ex.getDetails())
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }

    private HttpStatus mapStatus(DomainException ex) {
        return statusTable.getOrDefault(ex.getClass().getSimpleName(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
