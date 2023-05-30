package com.blog.app.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blog.app.biz.IBlogPostsBiz;
import com.blog.app.dao.entity.BlogPosts;
import com.blog.app.dto.BaseResponse;
import com.blog.app.dto.BlogPostsDTO;
import com.blog.app.dto.BlogPostsDeleteDTO;
import com.blog.app.dto.BlogPostsFilterDTO;
import com.blog.app.dto.BlogPostsQueryDTO;
import com.blog.app.dto.BlogPostsUpdateDTO;
import com.blog.app.util.BlogPostsFilter;
import com.blog.app.util.BlogPostsMapper;

@RestController
@RequestMapping("/blogposts")
public class BlogPostsController extends BaseController{

	@Autowired
	private IBlogPostsBiz blogPostsBiz;
	
	@Autowired
	private BlogPostsMapper mapper;
	
	@PostMapping("/findAll")
	public BaseResponse<BlogPostsQueryDTO> findAllByFilter(@RequestBody BlogPostsFilterDTO dto) {
		BlogPostsFilter filter = mapper.mappingBlogPostsFilter(dto);
		List<BlogPostsQueryDTO.BlogPostsQuery> blogPostsQuery = new ArrayList<>();
		try {
			Page<BlogPosts> blogPostsPage = blogPostsBiz.findAllByFilter(filter);
			BigInteger totalElements = BigInteger.valueOf(blogPostsPage.getTotalElements());
			
			for (BlogPosts blogPosts:blogPostsPage) {
				BlogPostsQueryDTO.BlogPostsQuery teamQueryDTO = mapper.entityBlogPostsQueryToDTO(blogPosts);
				blogPostsQuery.add(teamQueryDTO);
			}
			BlogPostsQueryDTO response = new BlogPostsQueryDTO();
			response.setCount(blogPostsQuery.size());
			response.setTotalElements(totalElements);
			response.setBlogPostsQuery(blogPostsQuery);
			
			return new BaseResponse<BlogPostsQueryDTO>(response);
			
		}catch (Exception e) {
			return new BaseResponse<BlogPostsQueryDTO>(HttpStatus.BAD_REQUEST, "error while get data, causedby : "+ e, null);
		}
	}
	
	@GetMapping("/findById")
	public BaseResponse<BlogPostsDTO> findById(@RequestParam Long id) {
		
		Optional<BlogPosts> optBlogPosts = blogPostsBiz.findById(id);
		if (!optBlogPosts.isPresent()) {
			return new BaseResponse<BlogPostsDTO>(HttpStatus.NOT_FOUND, "id : "+id+ " doesn't exist!", null);
		}
		BlogPosts blogPosts = optBlogPosts.get();
		BlogPostsDTO response = mapper.blogPostsEntityToDTO(blogPosts);
		
		return new BaseResponse<BlogPostsDTO>(response);
	}
	
	@PostMapping("/save")
	public BaseResponse<BlogPostsDTO> save(@Valid @RequestBody BlogPostsDTO dto) {		
		
		Optional<BlogPosts> optBlogPosts = blogPostsBiz.findByTitle(dto.getTitle());
		if (optBlogPosts.isPresent()) {
			return new BaseResponse<BlogPostsDTO>(HttpStatus.BAD_REQUEST, "title : "+dto.getTitle()+" already exists!", null);
		}
		
		try{
			BlogPosts blogPosts = mapper.blogPostsDtoToEntity(dto);
			blogPostsBiz.save(blogPosts);
			return new BaseResponse<BlogPostsDTO>(HttpStatus.OK, "Successfully save ", null);
		}catch (Exception e) {
			return new BaseResponse<BlogPostsDTO>(HttpStatus.BAD_REQUEST, "error while saving data, causedby : "+ e, null);
		}
	}
	
	@PostMapping("/update")
	public BaseResponse<BlogPostsUpdateDTO> update(@Valid @RequestBody BlogPostsUpdateDTO dto) {		
		
		Optional<BlogPosts> optBlogPosts = blogPostsBiz.findById(dto.getId());
		if (!optBlogPosts.isPresent()) {
			return new BaseResponse<BlogPostsUpdateDTO>(HttpStatus.NOT_FOUND, "id : "+dto.getId()+ " doesn't exist!", null);
		}
		
		try{
			BlogPosts blogPosts = optBlogPosts.get();	
			BlogPosts mappedBlogPosts = mapper.blogPostsEditMapper(blogPosts, dto);
			blogPostsBiz.save(mappedBlogPosts);
			return new BaseResponse<BlogPostsUpdateDTO>(HttpStatus.OK, "Successfully updated id : "+dto.getId(), null);
		}catch (Exception e) {
			return new BaseResponse<BlogPostsUpdateDTO>(HttpStatus.BAD_REQUEST, "error while saving data, causedby : "+ e, null);
		}
	}
	
	@PostMapping("/delete")
	public BaseResponse<BlogPostsDeleteDTO> delete(@Valid @RequestBody BlogPostsDeleteDTO dto) {		
		
		Optional<BlogPosts> optBlogPosts = blogPostsBiz.findById(dto.getId());
		if (!optBlogPosts.isPresent()) {
			return new BaseResponse<BlogPostsDeleteDTO>(HttpStatus.NOT_FOUND, "id : "+dto.getId()+ " doesn't exist!", null);
		}
		
		try{
			BlogPosts blogPosts = optBlogPosts.get();	
			BlogPosts mappedBlogPosts = mapper.blogPostsDeleteMapper(blogPosts, dto);
			blogPostsBiz.save(mappedBlogPosts);
			return new BaseResponse<BlogPostsDeleteDTO>(HttpStatus.OK, "Successfully delete id : "+dto.getId(), null);
		}catch (Exception e) {
			return new BaseResponse<BlogPostsDeleteDTO>(HttpStatus.BAD_REQUEST, "error while saving data, causedby : "+ e, null);
		}
	}
	
}
