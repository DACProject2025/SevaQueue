	package com.sevaqueue.exception;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.sevaqueue.dto.ApiResponseDto;
import com.sevaqueue.service.LoggerClient;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final LoggerClient loggerClient;

    GlobalExceptionHandler(LoggerClient loggerClient) {
        this.loggerClient = loggerClient;
    }

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseDto> handleResourceNotFound(ResourceNotFoundException ex) {
		loggerClient.log("ERROR", ex.getMessage());
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new ApiResponseDto(ex.getMessage(), false));
	}
	
	@ExceptionHandler(UnauthorizedActionException.class)
	public ResponseEntity<ApiResponseDto> handleUnauthorizedAction(UnauthorizedActionException ex) {
		loggerClient.log("ERROR", ex.getMessage());
		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(new ApiResponseDto(ex.getMessage(), false));
	}
	
	@ExceptionHandler(QueueEmptyException.class)
	public ResponseEntity<ApiResponseDto> handleQueueEmpty(QueueEmptyException ex) {
		loggerClient.log("ERROR", ex.getMessage());
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ApiResponseDto(ex.getMessage(), false));
	}
	
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<ApiResponseDto> handleInvalidRequest(InvalidRequestException ex) {
		loggerClient.log("ERROR", ex.getMessage());
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(new ApiResponseDto(ex.getMessage(), false));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseDto> handleGeneric(Exception ex) {
		loggerClient.log("ERROR", ex.getMessage());
		return ResponseEntity
				.badRequest()
				.body(new ApiResponseDto(ex.getMessage(), false));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
		List<FieldError> fieldError = methodArgumentNotValidException.getFieldErrors();
		Map<String, String> errorFieldMap = fieldError
				.stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		loggerClient.log("ERROR", "Validation failed: " + errorFieldMap.toString());
		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(errorFieldMap);
	}

	
}
