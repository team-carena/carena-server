package org.sopt.carena.global.exception.handler;

import org.sopt.carena.global.api.response.ApiResponse;
import org.sopt.carena.global.exception.BaseException;
import org.sopt.carena.global.exception.code.ErrorCode;
import org.sopt.carena.member.exception.code.MemberErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler {

	@ExceptionHandler(BaseException.class)
	protected ResponseEntity<ApiResponse> handleBaseException(BaseException e) {
		return buildErrorResponse(e.getErrorResultCode());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<ApiResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e) {
		return buildErrorResponse(ErrorCode.INVALID_REQUEST_MESSAGE);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ApiResponse> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException e) {
		return buildErrorResponse(ErrorCode.INVALID_REQUEST_MESSAGE);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		return buildErrorResponse(ErrorCode.INVALID_REQUEST_MESSAGE);
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	protected ResponseEntity<ApiResponse> handleMethodValidationException(HandlerMethodValidationException e) {
		return buildErrorResponse(ErrorCode.INVALID_REQUEST_MESSAGE);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	protected ResponseEntity<ApiResponse> handleNoHandlerFoundException(NoHandlerFoundException e) {
		return buildErrorResponse(ErrorCode.INVALID_ENDPOINT);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ApiResponse> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException e) {
		return buildErrorResponse(ErrorCode.INVALID_REQUEST_METHOD);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<ApiResponse> handleException(Exception e) {
		e.printStackTrace();
		return buildErrorResponse(ErrorCode.UNDEFINED_ERROR);
	}

	@ExceptionHandler(MissingRequestCookieException.class)
	protected ResponseEntity<ApiResponse> handleMissingRequestCookie(MissingRequestCookieException e) {
		return buildErrorResponse(MemberErrorCode.NOT_EXIST_TOKEN);
	}
}
