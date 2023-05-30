package com.blog.app.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogPostsQueryDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("count")
	public int count;
	
	@JsonProperty("totalElements")
	public BigInteger totalElements;
	
	@JsonProperty("blogPostsQuery")
	public List<BlogPostsQuery> blogPostsQuery;
	
	@Data
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class BlogPostsQuery implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		@JsonProperty("id")
		public Long id;
		
		@JsonProperty("title")
		private String title;

		@JsonProperty("body")
		private String body;

		@JsonProperty("author")
		private String author;

		@JsonProperty("isDelete")
		private Boolean isDelete;
		
		@JsonProperty("createdBy")
		public String createdBy;
		
		@JsonProperty("createdTime")
		public Date createdTime;

		@JsonProperty("modifiedBy")
		public String modifiedBy;
		
		@JsonProperty("modifiedTime")
		public Date modifiedTime;

	}
	
}
