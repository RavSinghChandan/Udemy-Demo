package com.wrappiza.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.wrappiza.application.exception.ApiError;
import com.wrappiza.application.exception.ErrorCategory;
import com.wrappiza.application.exception.ServiceTerminalException;

/**
 * Catching every exceptions in all controllers in the project
 * 
 * @author Chandan Kumar
 *
 */
@ControllerAdvice
public class GlobalControllerAdvice {

	/**
	 * Handling ServiceTerminalException with Api Error fields name
	 * 
	 * @param ex
	 * 
	 */
	@ExceptionHandler(ServiceTerminalException.class)
	public ResponseEntity<ApiError> handleServiceException(ServiceTerminalException ex) {

		HttpStatus status = (null != ex.getErrorCode()) ? HttpStatus.valueOf(ex.getErrorCategory().name())
				: HttpStatus.INTERNAL_SERVER_ERROR;

		ApiError apiError = new ApiError();

		apiError.setCode(ex.getErrorCode());
		apiError.setStatus(ErrorCategory.valueOf(status.name()));
		apiError.setMessage(ex.getMessage());

		return new ResponseEntity<>(apiError, status);
	}
}