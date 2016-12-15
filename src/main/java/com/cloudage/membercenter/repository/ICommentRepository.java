package com.cloudage.membercenter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.Comment;

public interface ICommentRepository extends PagingAndSortingRepository<Comment, Integer>{
	
	@Query("from Comment comment where comment.article.id = ?1")
	Page<Comment> findAllByArticleId(Integer articleId,Pageable pageable);

	@Query("from Comment comment where comment.author.id = ?1")
	Page<Comment> findMyComment(Integer id, Pageable pageable);
	@Query("from Comment comment where comment.article.author.id = ?1")
	Page<Comment> findCommentToMe(Integer id, Pageable pageable);
	
	
}