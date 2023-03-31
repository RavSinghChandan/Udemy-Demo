package com.wrappiza.application.exception;

import lombok.AllArgsConstructor;

/**
 * TerminalDBException custom exception class
 * 
 * @author Chandan Kumar
 *
 */
@AllArgsConstructor
public class TerminalDBException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TerminalDBException(String message) {

		super(message);
	}

}