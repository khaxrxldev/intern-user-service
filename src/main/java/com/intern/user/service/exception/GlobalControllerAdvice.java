package com.intern.user.service.exception;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.intern.user.service.dto.Response;

@ControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<Response> handleDataNotFoundException(DataNotFoundException dataNotFoundException) {
		Response response = new Response();
		
		response.setTime_stamp(LocalDateTime.now());
		response.setStatus_code(HttpStatus.NOT_FOUND.value());
		response.setStatus_desc(HttpStatus.NOT_FOUND);
		response.setError_desc(dataNotFoundException.getErrorMessage());
		response.setMessage_status(false);
		response.setMessage_desc("");
		response.setMessage_code("");
		response.setMessage_dev("");
		response.setData(null);
		
		return ResponseEntity.ok(response);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Response> handleRuntimeException(RuntimeException runtimeException) {
		Response response = new Response();
		
		response.setTime_stamp(LocalDateTime.now());
		response.setStatus_code(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setStatus_desc(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setError_desc("Process failed");
		response.setMessage_status(false);
		response.setMessage_desc("");
		response.setMessage_code("");
		response.setMessage_dev("");
		response.setData(null);
		
		return ResponseEntity.ok(response);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Response> handleException(Exception exception) {
		Response response = new Response();
		
		response.setTime_stamp(LocalDateTime.now());
		response.setStatus_code(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setStatus_desc(HttpStatus.INTERNAL_SERVER_ERROR);
		response.setError_desc(exception.getMessage());
		response.setMessage_status(false);
		response.setMessage_desc("");
		response.setMessage_code("");
		response.setMessage_dev("");
		response.setData(null);
		
		return ResponseEntity.ok(response);
	}
}
