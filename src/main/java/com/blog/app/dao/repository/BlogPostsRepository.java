package com.blog.app.dao.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.blog.app.dao.entity.BlogPosts;

@Repository
public interface BlogPostsRepository extends JpaRepository<BlogPosts, Long>, JpaSpecificationExecutor<BlogPosts>{

	@Query(value = "SELECT b FROM BlogPosts b WHERE b.isDelete = false "
			+ "AND (cast(?1 as string) is null OR b.id = cast (cast(?1 as string) as int)) "
			+ "AND (cast(?2 as string) is null OR b.title like concat('%' ,cast(?2 as string), '%'))",
			countQuery = " SELECT count(*) "+
					" FROM BlogPosts b WHERE b.isDelete = false "
					+ "AND (cast(?1 as string) is null OR b.id = cast (cast(?1 as string) as int)) "
					+ "AND (cast(?2 as string) is null OR b.title like concat('%' ,cast(?2 as string), '%'))")
	Page<BlogPosts> findAllBlogPosts(Long id, String title, Pageable page);

	Optional<BlogPosts> findByTitle(String title);

	Optional<BlogPosts> findByIdAndIsDelete(Long id, Boolean isDelete);

}
