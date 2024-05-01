package com.flyease.flyeaseapirest.exception;

import com.flyease.flyeaseapirest.model.payload.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // Controla los errores de los campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                          WebRequest webRequest) {
        Map<String, String> mapErrors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
                    String clave = ((FieldError) error).getField();
                    String valor = error.getDefaultMessage();
                    mapErrors.put(clave, valor);
                }
        );
        ApiResponse apiResponse = new ApiResponse(mapErrors.toString(), false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    // Controla los errores not found 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException (ResourceNotFoundException exception,
                                                                         WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), false);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    // Controla los errores de lógica o de los catch en general 400
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> handlerBadRequestException(BadRequestException exception,
                                                                  WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), false);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Controla los errores de varios tipos y globalizarlo con un error 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handlerException(Exception exception,
                                                        WebRequest webRequest) {
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), false);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

}