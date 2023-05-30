package com.blog.app.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.blog.app.dao.entity.BlogPosts;
import com.blog.app.dto.BlogPostsDTO;
import com.blog.app.dto.BlogPostsDeleteDTO;
import com.blog.app.dto.BlogPostsFilterDTO;
import com.blog.app.dto.BlogPostsQueryDTO;
import com.blog.app.dto.BlogPostsQueryDTO.BlogPostsQuery;
import com.blog.app.dto.BlogPostsUpdateDTO;

@Component
public class BlogPostsMapper {

	public static final List<String> SORTING_METHOD = Arrays.asList("ASC","DESC");
	
	public BlogPostsFilter mappingBlogPostsFilter(BlogPostsFilterDTO dto) {
		PageRequest pagination = validatePagination(dto.getPage(), dto.getSize(), dto.getSortBy(), dto.getSortMethod());
		BlogPostsFilter.BlogPostsFilterBuilder filter = BlogPostsFilter.builder()
				.id(dto.getId())
				.title(dto.getTitle())
				.pagination(pagination)
				.limit(dto.getSize());
		
		return filter.build();
	}

	private PageRequest validatePagination (int page, int size, String sortBy, String sortMethod) {
		PageRequest pageable = PageRequest.of(page < 0 ? 0 : page, size < 0 ? 10 : size, Sort.Direction.DESC, "id");
		if (StringUtils.equals(StringUtils.upperCase(sortMethod), SORTING_METHOD.get(0))) {
			pageable = PageRequest.of(page < 0 ? 0 : page, size < 0 ? 10 : size, Sort.Direction.ASC, sortBy);
		}
		if(StringUtils.equals(StringUtils.upperCase(sortMethod), SORTING_METHOD.get(1))){
			pageable = PageRequest.of(page < 0 ? 0 : page, size < 0 ? 10 : size, Sort.Direction.DESC, sortBy);
	
		}
		return pageable;
	}
	
	public BlogPostsQuery entityBlogPostsQueryToDTO(BlogPosts blogPosts) {
		BlogPostsQueryDTO.BlogPostsQuery dto = new BlogPostsQueryDTO.BlogPostsQuery();
		BeanUtils.copyProperties(blogPosts, dto);
		dto.setId(blogPosts.getId());
		
		dto.setModifiedBy(blogPosts.getModifiedBy());
		dto.setModifiedTime(blogPosts.getModifiedTimestamp());
		dto.setCreatedBy(blogPosts.getCreatedBy());
		dto.setCreatedTime(blogPosts.getCreatedTimestamp());

		return dto;
	}
	
	public BlogPosts blogPostsDtoToEntity(BlogPostsDTO dto) {
		BlogPosts entity = new BlogPosts();
		BeanUtils.copyProperties(dto, entity);
		entity.setCreatedBy(dto.getCreatedBy() == null ? "SYSTEM" : dto.getCreatedBy());
		entity.setCreatedTimestamp(new Date());
		return entity;
	}

	public BlogPosts blogPostsEditMapper(BlogPosts blogPosts, BlogPostsUpdateDTO dto) {
		
		blogPosts.setTitle(dto.getTitle());
		blogPosts.setBody(dto.getBody());
		blogPosts.setAuthor(dto.getAuthor());
		blogPosts.setIsDelete(dto.getIsDelete());
		
		blogPosts.setModifiedBy(dto.getModifiedBy());
		blogPosts.setModifiedTimestamp(new Date());
		
		return blogPosts;
	}
	
	public BlogPosts blogPostsDeleteMapper(BlogPosts blogPosts, BlogPostsDeleteDTO dto) {
		
		blogPosts.setIsDelete(true);
		blogPosts.setModifiedBy(dto.getModifiedBy());
		blogPosts.setModifiedTimestamp(new Date());
		
		return blogPosts;
	}

	public BlogPostsDTO blogPostsEntityToDTO(BlogPosts entity) {
		BlogPostsDTO dto = new BlogPostsDTO();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}
}
