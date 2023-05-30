package com.blog.app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.app.dao.entity.BlogPosts;
import com.blog.app.dao.repository.BlogPostsRepository;
import com.blog.app.service.IBlogPostsService;
import com.blog.app.util.BlogPostsFilter;

@Service
public class BlogPostsServiceImpl implements IBlogPostsService {

	@Autowired
	private BlogPostsRepository blogPostsRepo;

	@Override
	public BlogPosts save(BlogPosts blogPosts) {
		return blogPostsRepo.save(blogPosts);
	}

	@Override
	public Optional<BlogPosts> findById(Long id) {
		return blogPostsRepo.findByIdAndIsDelete(id, false);
	}

	@Override
	public Optional<BlogPosts> findByTitle(String title) {
		return blogPostsRepo.findByTitle(title);
	}
	
	@Override
	public Page<BlogPosts> findAllByFilter(BlogPostsFilter filter) {
		
		PageRequest pagination = filter.getPagination();
		Sort sort = pagination.getSort();
		
		return blogPostsRepo.findAllBlogPosts(filter.getId(), filter.getTitle(),
						PageRequest.of(pagination.getPageNumber(), pagination.getPageSize(), sort));
	}

}
