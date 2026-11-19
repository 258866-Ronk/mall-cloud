package com.mallcloud.common.error;

import com.mallcloud.common.api.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(BizException.class)
    public ApiResponse<Void> handleBiz(BizException e) { return ApiResponse.failure(e.getCode(), e.getMessage()); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream().map(x -> x.getField() + ": " + x.getDefaultMessage()).collect(Collectors.joining("; "));
        return ApiResponse.failure(ErrorCode.BAD_REQUEST.code(), message);
    }
    @ExceptionHandler({ConstraintViolationException.class, HttpMessageNotReadableException.class})
    public ApiResponse<Void> handleBadRequest(Exception e) { return ApiResponse.failure(ErrorCode.BAD_REQUEST.code(), e.getMessage()); }
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleUnknown(Exception e) { log.error("Unhandled request exception", e); return ApiResponse.failure(500, ErrorCode.INTERNAL_ERROR.message()); }
}
