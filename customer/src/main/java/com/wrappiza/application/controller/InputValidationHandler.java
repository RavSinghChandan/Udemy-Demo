package com.wrappiza.application.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wrappiza.application.exception.ApiError;
import com.wrappiza.application.exception.ErrorCategory;

/**
 * Catching exceptions for input validation in CreateMenuRequestInfo DTO class
 * 
 * @author Chandan Kumar
 *
 */
@ControllerAdvice
public class InputValidationHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handling inpur validation exception with ApiError fields name
	 * 
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 */
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, ApiError> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {

			String fieldName = ((FieldError) error).getField();

			ApiError apiError = new ApiError();

			apiError.setCode(String.valueOf(status.value()));
			apiError.setStatus(ErrorCategory.valueOf(status.name()));
			apiError.setMessage(error.getDefaultMessage());

			errors.put(fieldName, apiError);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
}