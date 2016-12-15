package com.cloudage.membercenter.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cloudage.membercenter.entity.Likes;

public interface ILisksRepository extends PagingAndSortingRepository<Likes, Likes.Key>{

	@Query("select count(*) from Likes likes where likes.id.article.id = ?1")
	int countLikes(int article_id);
	
	@Query("from Likes likes where likes.id.user.id = ?1 and likes.id.article.id = ?2")
	Likes checkLiked(int user_id,int article_id);





}