package com.cloudage.membercenter.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cloudage.membercenter.entity.Article;
import com.cloudage.membercenter.entity.Comment;
import com.cloudage.membercenter.entity.Likes;
import com.cloudage.membercenter.entity.User;

public interface ILikesService {
	Likes save(Likes likes);

	int countLikes(int article_id);

	boolean checkLiked(int user_id,int article_id);

	Likes addLikes(User me,Article article);

	void removeLikes(User me,Article article);


}