package com.blog.app.biz.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.blog.app.biz.IBlogPostsBiz;
import com.blog.app.dao.entity.BlogPosts;
import com.blog.app.service.IBlogPostsService;
import com.blog.app.util.BlogPostsFilter;

@Component
public class BlogPostsBizImpl implements IBlogPostsBiz{

	@Autowired
	private IBlogPostsService blogPostsService;
	
	@Override
	public BlogPosts save(BlogPosts blogPosts) {
		return blogPostsService.save(blogPosts);
	}

	@Override
	public Optional<BlogPosts> findById(Long id) {
		return blogPostsService.findById(id);
	}

	@Override
	public Optional<BlogPosts> findByTitle(String title) {
		return blogPostsService.findByTitle(title);
	}
	
	@Override
	public Page<BlogPosts> findAllByFilter(BlogPostsFilter filter) {
		return blogPostsService.findAllByFilter(filter);
	}

}
