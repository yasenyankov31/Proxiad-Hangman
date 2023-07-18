package com.yasen;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(Exception.class) // replace this with your exception type
	protected ResponseEntity<String> validate(Exception ex) {

		return new ResponseEntity<>("Something went wrong " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
