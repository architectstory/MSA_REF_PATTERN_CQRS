package com.samsungsds.msa.biz.order.command.infrastructure.exceptions;

import com.samsungsds.msa.cqrs.dto.BaseResponse;
import org.apache.kafka.common.errors.TimeoutException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.KafkaException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @Value("Client made a bad request - {0}")
    private String exceptionMsg1;

    @Value("${exception2.error.msg}")
    private String exceptionMsg2;

    @Value("Kafka Exception - {0}")
    private String exceptionMsg3;

    @Value("Error whilw placing the order {0}")
    private String exceptionMsg4;

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<BaseResponse> handleIllegalStateException(IllegalStateException e) {
        var errorMessage = MessageFormat.format(exceptionMsg1, e.getMessage());
        logger.log(Level.WARNING, errorMessage);
        return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(KafkaException.class)
    public ResponseEntity<BaseResponse> handleEKafkaException(KafkaException e) {
        if(e.getCause() instanceof TimeoutException) {
            var errorMessage = MessageFormat.format(exceptionMsg2, e.getMessage());
            logger.log(Level.SEVERE, errorMessage);
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.SERVICE_UNAVAILABLE);
        } else {
            var errorMessage = MessageFormat.format(exceptionMsg3, e.getMessage());
            logger.log(Level.SEVERE, errorMessage);
            return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse> handleException(Exception e) {
        var errorMessage = MessageFormat.format(exceptionMsg4, e.getMessage());
        logger.log(Level.SEVERE, errorMessage);
        return new ResponseEntity<>(new BaseResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
