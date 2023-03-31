package com.wrappiza.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ServiceTerminalException custom exception class
 * 
 * @author Chandan Kumar
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ServiceTerminalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/*
	 * Constructor for ServiceTerminalException with error code and error category
	 */
	public ServiceTerminalException(ServiceErrorCode serviceErrorCode) {
		super(serviceErrorCode.getMessage());
		this.errorCode = serviceErrorCode.getCode();
		this.errorCategory = serviceErrorCode.getErrorCategory();
	}

	private String errorCode;
	private ErrorCategory errorCategory;

}