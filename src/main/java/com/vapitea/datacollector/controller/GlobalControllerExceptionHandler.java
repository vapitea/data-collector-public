package com.vapitea.datacollector.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

  @ExceptionHandler()
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public void handleWrongJsonFormat(MethodArgumentNotValidException ex) {
    log.info("Invalid DTO: " + ex.getMessage());
  }

  @ExceptionHandler()
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public void handleWrongJsonFormat(NoSuchElementException ex) {

    log.info("Missing resource: " + ex.getMessage());
  }


  @ExceptionHandler(NoHandlerFoundException.class)
  public String redirect404ToSinglePageApp() {
    return "redirect:/index.html";
  }
}
