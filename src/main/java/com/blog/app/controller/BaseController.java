package com.blog.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.exception.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BaseController {

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The requested resource isn't available")
	@ExceptionHandler({EntityNotFoundException.class })
	public void handleEntityNotFoundException(RuntimeException ex) {
		log.error("", ex);
	}
	
}
