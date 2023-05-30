package com.blog.app.util;

import org.springframework.data.domain.PageRequest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlogPostsFilter {

	private Long id;	
	private String title;
	private int limit;
	private PageRequest pagination;
	
	
}
