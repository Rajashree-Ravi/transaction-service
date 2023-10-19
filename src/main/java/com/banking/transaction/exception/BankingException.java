package com.banking.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BankingException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6493749301355949996L;
	private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;
}
