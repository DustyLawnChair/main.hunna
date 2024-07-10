package com.PlayTrackerWebApp.playtracker.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(StatsException.class)
    public ResponseEntity<ExceptionResponse> handleStatsException(StatsException statsException, HttpServletRequest request)
    {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setMessage(statsException.getMessage());
        exceptionResponse.setRequestURI(request.getRequestURI());

        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
