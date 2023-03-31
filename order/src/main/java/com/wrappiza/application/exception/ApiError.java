package com.wrappiza.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Enum Custom error fields name for our exception
 * 
 * @author Chandan Kumar
 *
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class ApiError {

	private ErrorCategory status;
	private String code;
	private String message;

}