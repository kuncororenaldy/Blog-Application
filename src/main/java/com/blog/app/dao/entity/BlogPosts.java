package com.blog.app.dao.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "trx_blog_posts")
public class BlogPosts implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "body")
	private String body;

	@Column(name = "author")
	private String author;

	@Column(name = "is_delete")
	private Boolean isDelete;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "created_time")
	private Date createdTimestamp;
		
	@Column(name = "created_by")
	private String createdBy;
		
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "modified_time")
	private Date modifiedTimestamp;
		
	@Column(name = "modified_by")
	private String modifiedBy;
	  
}
