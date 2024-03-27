package com.samsungsds.msa.biz.order.infrastructure.exceptions;

import com.samsungsds.msa.cqrs.dto.BaseResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @Value("Failed to complete order request - {0}")
    private String failedErrorMsg;

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<BaseResponse> handleOrderNotFoundException(Exception e) {
        var errorMessage = e.getMessage();
        logger.log(Level.SEVERE, errorMessage);
        return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(Exception e) {
        var errorMessage = MessageFormat.format(failedErrorMsg, e.getMessage());
        logger.log(Level.SEVERE, errorMessage);
        return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
