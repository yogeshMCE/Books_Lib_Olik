package com.example.bookLibrary.Exceptions;

import com.example.bookLibrary.DTOs.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourseNotFoundException.class)
    ResponseEntity<Response> resourseNotFoundEcxeptionHandler(ResourseNotFoundException ex){
        String Message = ex.getMessage();
        Response response= new Response(Message,false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String,String>>methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        Map<String,String> res= new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((objectError) -> {
            String FieldName= ((FieldError)objectError).getField();
            String Message= objectError.getDefaultMessage();
            res.put(FieldName,Message);
        });
        return new ResponseEntity<>(res,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<Response>runTimeException(RuntimeException ex){
        String Message = ex.getMessage();
        Response response= new Response(Message,false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
