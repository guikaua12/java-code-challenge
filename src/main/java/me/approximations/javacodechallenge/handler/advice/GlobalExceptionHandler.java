package me.approximations.javacodechallenge.handler.advice;

import me.approximations.javacodechallenge.handler.enums.ErrorEnum;
import me.approximations.javacodechallenge.handler.exception.StatusCodeException;
import me.approximations.javacodechallenge.handler.response.CustomErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StatusCodeException.class)
    public ResponseEntity<Object> handleStatusCodeException(StatusCodeException exception, WebRequest request) {
        return createCustomResponse(exception.getStatus(), exception.getMessage(), exception, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders _headers, HttpStatusCode status, WebRequest request) {
        final String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return createCustomResponse(status, message, ex, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleStatusCodeException(BadCredentialsException exception, WebRequest request) {
        return createCustomResponse(ErrorEnum.BAD_PASSWORD.getStatusCode(), ErrorEnum.BAD_PASSWORD.getMessage(), exception, request);
    }

    private ResponseEntity<Object> createCustomResponse(HttpStatusCode status, String message, Exception exception, WebRequest request) {
        final CustomErrorResponse response = new CustomErrorResponse(status.value(), message);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(exception, response, headers, status, request);
    }
}
