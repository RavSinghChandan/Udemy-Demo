package com.wrappiza.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Enum field name of service Error code
 * 
 * @author Chandan Kumar
 *
 */
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public enum ServiceErrorCode {

	DB_SEARCH_FAILURE("DB.SEARCH.FAILURE", "Failed to search records in database", ErrorCategory.BAD_REQUEST),
	DB_INSERTION_FAILURE("DB.INSERTION.FAILURE", "Failed to save records in database", ErrorCategory.BAD_REQUEST),
	EMPTY_SEARCH_FAILURE("EMPTY.SEARCH.FAILURE", "Failed to search records due to empty id or name",
			ErrorCategory.BAD_REQUEST),
	DB_UPDATION_FAILURE("DB_UPDATION_FAILURE", "Failed to update records in database", ErrorCategory.BAD_REQUEST),
	DB_DELETION_FAILURE("DB.DELETION.FAILURE", "Failed to delete records in database", ErrorCategory.BAD_REQUEST);

	private String code;
	private String message;
	private ErrorCategory errorCategory;

}