package com.flyease.flyeaseapirest.exception;

import com.flyease.flyeaseapirest.model.payload.InformeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    // Controla los errores de los campos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                          WebRequest webRequest) {
        Map<String, String> mapErrors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
                    String clave = ((FieldError) error).getField();
                    String valor = error.getDefaultMessage();
                    mapErrors.put(clave, valor);
                }
        );
        InformeResponse informeResponse = new InformeResponse(mapErrors.toString(), false);
        return new ResponseEntity<>(informeResponse, HttpStatus.BAD_REQUEST);
    }

    // Controla los errores not found 404
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<InformeResponse> handlerResourceNotFoundException (ResourceNotFoundException exception,
                                                                             WebRequest webRequest) {
        InformeResponse informeResponse = new InformeResponse(exception.getMessage(), false);
        return new ResponseEntity<>(informeResponse, HttpStatus.NOT_FOUND);
    }

    // Controla los errores de lógica o de los catch en general 400
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<InformeResponse> handlerBadRequestException(BadRequestException exception,
                                                                      WebRequest webRequest) {
        InformeResponse informeResponse = new InformeResponse(exception.getMessage(), false);
        return new ResponseEntity<>(informeResponse, HttpStatus.BAD_REQUEST);
    }

    // Controla los errores de varios tipos y globalizarlo con un error 500
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<InformeResponse> handlerException(Exception exception,
                                                            WebRequest webRequest) {
        InformeResponse informeResponse = new InformeResponse(exception.getMessage(), false);
        return new ResponseEntity<>(informeResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
