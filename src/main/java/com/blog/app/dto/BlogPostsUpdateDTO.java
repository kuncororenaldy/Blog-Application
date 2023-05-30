package com.blog.app.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogPostsUpdateDTO  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@JsonProperty("id")
	private Long id;
	
	@NotBlank
	@JsonProperty("title")
	private String title;
	
	@NotBlank
	@JsonProperty("body")
	private String body;

	@NotBlank
	@JsonProperty("author")
	private String author;
	
	@JsonProperty("isDelete")
	private Boolean isDelete;
	
	@JsonProperty("modifiedBy")
	private String modifiedBy;

}
