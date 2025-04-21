package com.hylastix.fridgeservice.exceptions;

import com.hylastix.fridgeservice.exceptions.custom.ResourceNotFoundException;
import com.hylastix.fridgeservice.exceptions.response.RestApiErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RestApiErrorResponse> handleNotFoundException(ResourceNotFoundException exc) {
        logger.error("FridgeItem Not Found: ", exc);
        RestApiErrorResponse errorResponse = RestApiErrorResponse.builder()
                .error("FridgeItem Not Found")
                .message(exc.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestApiErrorResponse> handleIllegalArgument(IllegalArgumentException exc) {
        logger.error("Illegal or Inappropriate argument passed exception: ", exc);
        RestApiErrorResponse errorResponse = RestApiErrorResponse.builder()
                .error("An Illegal or Inappropriate Argument has been passed to the method.")
                .message(exc.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestApiErrorResponse> handleGeneralException(Exception exception) {
        logger.error("Uncaught Exception: ", exception);
        RestApiErrorResponse errorResponse = RestApiErrorResponse.builder()
                .error("Internal Server Error")
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
