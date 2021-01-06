package com.vapitea.datacollector.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

  @ExceptionHandler()
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public void handleWrongJsonFormat(MethodArgumentNotValidException ex) {
    log.info("Invalid DTO: " + ex.getMessage());
  }

  @ExceptionHandler(NoSuchElementException.class)
  //@ResponseStatus(value = HttpStatus.NOT_FOUND)
  public void handleWrongJsonFormat(NoSuchElementException ex) {

    log.info("Missing resource: " + ex.getMessage());
  }

}
