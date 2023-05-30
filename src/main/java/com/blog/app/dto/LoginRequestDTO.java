package com.blog.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginRequestDTO {

	@NotBlank
	@JsonProperty("username")
	private String username; 
	
	@NotBlank
	@JsonProperty("password")
	private String password; 
	
}
