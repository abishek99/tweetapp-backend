package com.tweetapp.exception;

import com.tweetapp.enumerator.TweetUserEnum;
import com.tweetapp.vo.ErrorResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDate;

@ControllerAdvice
public class CustomExecptionHandler extends RuntimeException{

    @ExceptionHandler(value = TweetAppException.class)
    public ResponseEntity<ErrorResponse> tweetAppExecptionHandler(TweetAppException tweetApplicationException){
        ErrorResponse errorResponse = new ErrorResponse(tweetApplicationException.getMessage(), LocalDate.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException){
        ErrorResponse errorResponse = new ErrorResponse(methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage(), LocalDate.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> duplicateKeyExceptionHandler(DuplicateKeyException duplicateKeyException){
        ErrorResponse errorResponse = new ErrorResponse(String.valueOf(TweetUserEnum.EMAIL_PRESENT), LocalDate.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}

