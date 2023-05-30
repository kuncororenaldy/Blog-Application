package com.blog.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogPostsFilterDTO {

	@JsonProperty("page")
	private int page; 
	
	@JsonProperty("size")
	private int size; 

	@JsonProperty("sortBy")
	private String sortBy; 
	
	@JsonProperty("sortMethod")
	private String sortMethod;
	
	@JsonProperty("id")
	private Long id; 
	
	@JsonProperty("title")
	private String title; 
	
}
