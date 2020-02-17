package com.n26.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.n26.response.ResponseDataError;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.*;


@ControllerAdvice
public class ControllerAdviserExceptionHandler {

    public static final String MALFORMED_JSON = "Malformed JSON in request body";
    public static final String UNEXPECTED_CONDITION = "The server encounters an unexpected condition";
    public static final String TRANSACTION_IS_IN_THE_FUTURE = "Transaction is in the future";
    private ResponseDataError responseDataError;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDataError> defaultErrorHandler(Exception e) throws Exception {

        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }
        responseDataError = ResponseDataError.builder()
                .status(INTERNAL_SERVER_ERROR.value()).message(UNEXPECTED_CONDITION).build();
        return new ResponseEntity<ResponseDataError>(responseDataError, OK);
    }

    @ResponseBody
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDataError> HttpMessageNotReadableException(HttpMessageNotReadableException ex) {

    	if(ex.getRootCause().getClass().equals(MismatchedInputException.class)) {
            responseDataError = ResponseDataError.builder().status(BAD_REQUEST.value()).message(MALFORMED_JSON).build();
        } else
    		responseDataError = ResponseDataError.builder().status(UNPROCESSABLE_ENTITY.value()).message(TRANSACTION_IS_IN_THE_FUTURE).build();
        return new ResponseEntity<ResponseDataError>(responseDataError, HttpStatus.valueOf(responseDataError.getStatus()));
    }

}