package com.blog.app.dto;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class BaseResponse<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int status = HttpStatus.OK.value();
	
	private String info = HttpStatus.OK.name();
	
	private String message = "";
	
	private T content;
	
	public BaseResponse(T content) {
		this.content = content;
	}
	
	public BaseResponse(HttpStatus status, T content) {
		this.status = status.value();
		this.info = status.name();
		this.content = content;
	}
	
	public BaseResponse(HttpStatus status, String message, T content) {
		this.status = status.value();
		this.info = status.name();
		this.message = message;
		this.content = content;
	}
	
}

