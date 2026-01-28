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

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(ex.getMessage());
	}
	
	@ExceptionHandler(UnauthorizedActionException.class)
	public ResponseEntity<String> handleUnauthorizedAction(UnauthorizedActionException ex) {
		return ResponseEntity
				.status(HttpStatus.FORBIDDEN)
				.body(ex.getMessage());
	}
	
	@ExceptionHandler(QueueEmptyException.class)
	public ResponseEntity<String> handleQueueEmpty(QueueEmptyException ex) {
		return ResponseEntity
				.status(HttpStatus.NO_CONTENT)
				.body(ex.getMessage());
	}
	
	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<String> handleInvalidRequest(InvalidRequestException ex) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(ex.getMessage());
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneric(Exception ex) {
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Something went wrong!");
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
		List<FieldError> fieldError = methodArgumentNotValidException.getFieldErrors(null);
		Map<String, String> errorFieldMap = fieldError.stream()
				.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorFieldMap);
	}

	
}
