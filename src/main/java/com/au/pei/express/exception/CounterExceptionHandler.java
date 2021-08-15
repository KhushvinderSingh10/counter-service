package com.au.pei.express.exception;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.au.pei.express.model.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception handling Controller for different types of Exception
 */
@RestControllerAdvice
@Slf4j
public class CounterExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String INVALID_INPUT_PAYLOAD = "ER001";
	private static final String SYSTEM_FAILURE = "ER002";

	@ExceptionHandler({ConstraintViolationException.class})
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		log.error("ConstraintViolationException occurred {}", ex);
		ErrorResponse error = ErrorResponse.builder()
				.errorCode(INVALID_INPUT_PAYLOAD)
				.errorMessage("Required Field(s) are missing. Please check logs for more info")
				.build();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler({Exception.class})
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) {
		log.error("System Failure {}", ex);
		ErrorResponse error = ErrorResponse.builder()
				.errorCode(SYSTEM_FAILURE)
				.errorMessage(ex.getMessage())
				.build();
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@ExceptionHandler(TopParamException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleTopParamException(TopParamException ex, WebRequest request) {
		log.error("ProjectInputException occurred: {}", ex);
		ErrorResponse error = ErrorResponse.builder()
				.errorCode(INVALID_INPUT_PAYLOAD)
				.errorMessage(ex.getMessage())
				.build();
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}


	@Override
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("MethodArgumentNotValidException occurred : {}", ex.getMessage());
		List<String> errorList = ex
				.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(fieldError -> String.format("[%1$s [%2$s] %3$s]",
						fieldError.getField(),
						fieldError.getRejectedValue(),
						fieldError.getDefaultMessage()))
				.collect(Collectors.toList());

		ErrorResponse error = ErrorResponse.builder()
				.errorCode(INVALID_INPUT_PAYLOAD)
				.errorMessage(ex.getMessage())
				.errors(errorList)
				.build();
		return handleExceptionInternal(ex, error, headers, HttpStatus.BAD_REQUEST, request);
	}
}
