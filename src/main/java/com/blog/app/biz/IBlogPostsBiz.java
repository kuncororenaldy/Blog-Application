package com.blog.app.biz;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.blog.app.dao.entity.BlogPosts;
import com.blog.app.util.BlogPostsFilter;

public interface IBlogPostsBiz {

	BlogPosts save(BlogPosts blogPosts);
	
	Optional<BlogPosts> findById(Long id);
	
	Optional<BlogPosts> findByTitle(String title);
	
	Page<BlogPosts> findAllByFilter(BlogPostsFilter filter);
	
}
