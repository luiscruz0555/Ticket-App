package com.cognizant.exceptions.handlers;

import com.cognizant.exceptions.TicketNotFoundException;
import com.cognizant.exceptions.TicketNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.WebRequestInterceptor;
import org.springframework.web.server.WebHandler;
import reactor.netty.http.server.HttpServerRequest;

import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {TicketNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage ticketNotFoundException(TicketNotFoundException ex){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                "URI=/tickets/get-ticket/?");

        return message;
    }

    @ExceptionHandler(value = {TicketNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage ticketNotFoundException(TicketNotValidException ex){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                "URI=/tickets/new-ticket");

        return message;
    }

}
